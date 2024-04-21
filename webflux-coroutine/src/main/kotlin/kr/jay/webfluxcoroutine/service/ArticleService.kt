package kr.jay.webfluxcoroutine.service

import kotlinx.coroutines.flow.Flow
import kr.jay.webfluxcoroutine.config.CacheKey
import kr.jay.webfluxcoroutine.config.CacheManager
import kr.jay.webfluxcoroutine.config.extension.toLocalDate
import kr.jay.webfluxcoroutine.config.validator.DateString
import kr.jay.webfluxcoroutine.exception.NoArticleFoundException
import kr.jay.webfluxcoroutine.model.Article
import kr.jay.webfluxcoroutine.repository.ArticleRepository
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds

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
    private val dbClient: DatabaseClient,
    private val cache: CacheManager,
    redisTemplate: ReactiveRedisTemplate<Any, Any>,
) {

    private val ops = redisTemplate.opsForValue()

    init {
        cache.TTL["article/get"] = 10.seconds
    }

    suspend fun create(request: ReqCreate): Article {
        return repository.save(request.toArticle())
    }

    suspend fun get(id: Long): Article {
        val key = CacheKey("/article/get", id)
//        return cache.get(key)
//            ?: repository.findById(id)?.also{cache.set(key, it)}
//            ?: throw NoArticleFoundException("id: $id")
        return cache.get(key) { repository.findById(id) }
            ?: throw NoArticleFoundException("id: $id")
    }

    suspend fun getAll(title: String? = null): Flow<Article> {
        return if (title.isNullOrBlank()) {
            repository.findAll()
        } else {
            repository.findAllByTitleContains(title)
        }
    }

    suspend fun getAll(request: QryArticle): Flow<Article> {
        val params = HashMap<String, Any>()
        var sql = dbClient.sql(
            """
            SELECT id, title, body, author_id, created_at, updated_at
            FROM TB_ARTICLE
            WHERE 1=1
            ${
                request.title.query {
                    params["title"] = it.trim().let { "%$it%" }
                    "AND title LIKE :title"
                }
            }

            ${
                request.authorIds.query {
                    params["authorIds"] = it
                    "AND author_id IN (:authorIds)"
                }
            }
            ${
                request.from.query {
                    params["from"] = it.toLocalDate()
                    "AND created_at >= :from"
                }
            }
            ${
                request.to.query {
                    params["to"] = it.toLocalDate().plusDays(1)
                    "AND created_at < :to"
                }
            }
        """.trimIndent())
        params.forEach { (key, value) -> sql = sql.bind(key, value) }
        return sql.map { row ->
            Article(
                id = row.get("id") as Long,
                title = row.get("title") as String,
                body = row.get("body") as String,
                authorId = row.get("author_id") as Long,
            ).apply {
                createdAt = row.get("created_at") as LocalDateTime?
                updatedAt = row.get("updated_at") as LocalDateTime?
            }
        }.flow()
    }

//            if (request.title.isNullOrBlank()) "" else {
//                params["title"] = request.title.trim().let { "%$it%" }
//                "AND title LIKE :title"
//            }

    suspend fun delete(id: Long) {
        return repository.deleteById(id).also {
            val key = CacheKey("/article/get", id)
            cache.delete(key)
        }
    }

    suspend fun update(id: Long, request: ReqUpdate): Article {
        val article = repository.findById(id) ?: throw NoArticleFoundException("id: $id")
        return repository.save(article.apply {
            request.title?.let { this.title = it }
            request.body?.let { this.body = it }
            request.authorId?.let { this.authorId = it }
        }).also {
            val key = CacheKey("/article/get", id)
            cache.delete(key)
        }
    }
}

fun <T> T?.query(f: (T) -> String): String {
    return when {
        this == null -> ""
        this is String && this.isBlank() -> ""
        this is Collection<*> && this.isEmpty() -> ""
        else -> f.invoke(this)
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

data class QryArticle(
    val title: String?,
    val authorIds: List<Long>?,
    @DateString
    val from: String?,
    @DateString
    val to: String?,
)