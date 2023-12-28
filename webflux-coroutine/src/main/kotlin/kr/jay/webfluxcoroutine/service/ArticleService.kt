package kr.jay.webfluxcoroutine.service

import kotlinx.coroutines.flow.Flow
import kr.jay.webfluxcoroutine.exception.NoArticleFoundException
import kr.jay.webfluxcoroutine.model.Article
import kr.jay.webfluxcoroutine.repository.ArticleRepository
import org.springframework.stereotype.Service

/**
 * ArticleService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

@Service
class ArticleService(
    private val repository: ArticleRepository,
) {

    suspend fun create(request: ReqCreate): Article {
        return repository.save(request.toArticle())
    }

    suspend fun get(id: Long): Article {
        return repository.findById(id) ?: throw NoArticleFoundException("id: $id")
    }

    suspend fun getAll(title: String? = null): Flow<Article> {
        return if (title.isNullOrBlank()) {
            repository.findAll()
        } else {
            repository.findAllByTitleContains(title)
        }
    }

    suspend fun delete(id: Long) {
       return repository.deleteById(id)
    }
    suspend fun update(id: Long, request: ReqUpdate): Article {
        val article = repository.findById(id) ?: throw NoArticleFoundException("id: $id")
        return repository.save(article.apply {
            request.title?.let { this.title = it }
            request.body?.let { this.body = it }
            request.authorId?.let { this.authorId = it }
        })
    }
}

data class ReqUpdate(
    val title: String? = null,
    val body: String? = null,
    val authorId: Long? = null,
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