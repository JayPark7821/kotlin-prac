package kr.jay.webfluxreactor.service

import kr.jay.webfluxreactor.exception.NoArticleException
import kr.jay.webfluxreactor.model.Article
import kr.jay.webfluxreactor.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

/**
 * ArticleService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/26/23
 */

@Service
class ArticleService(
    private val repository: ArticleRepository,
) {

    @Transactional
    fun create(request: ReqCreate): Mono<Article> {
        return repository.save(request.toArticle())
    }

    fun get(id: Long): Mono<Article> {
        return repository.findById(id)
            .switchIfEmpty { throw NoArticleException("id: $id") }
    }

    fun getAll(title: String? = null): Flux<Article> {
        return if(title.isNullOrEmpty()) repository.findAll()
        else repository.findAllByTitleContains(title)
    }

    fun update(id: Long, request: ReqUpdate): Mono<Article> {
        return repository.findById(id)
            .switchIfEmpty { throw NoArticleException("id: $id") }
            .flatMap { article ->
                request.title?.let { article.title = it }
                request.body?.let { article.body = it }
                repository.save(article)
            }
    }

    fun delete(id: Long): Mono<Void> {
        return repository.findById(id)
            .switchIfEmpty { throw NoArticleException("id: $id") }
            .flatMap { repository.delete(it) }
    }
}

data class ReqUpdate(
    var title: String?,
    var body: String?,
)

data class ReqCreate(
    val title: String,
    val body: String,
    val authorId: Long,
) {

    fun toArticle(): Article {
        return Article(
            title = this.title,
            body = this.body,
            authorId = this.authorId,
        )
    }
}
