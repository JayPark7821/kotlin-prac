package kr.jay.webfluxcoroutine.repository

import kotlinx.coroutines.flow.Flow
import kr.jay.webfluxcoroutine.model.Article
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * ArticleRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

@Repository
interface ArticleRepository : CoroutineCrudRepository<Article, Long> {
    suspend fun findAllByTitleContains(title: String): Flow<Article>
}
