package kr.jay.webfluxreactor.repository

import kr.jay.webfluxreactor.model.Article
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

/**
 * ArticleRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/26/23
 */
@Repository
interface ArticleRepository : R2dbcRepository<Article, Long>{

    fun findAllByTitleContains(title: String): Flux<Article>
}