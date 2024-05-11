package kr.jay.elasticsearchprac.repository

import kotlinx.coroutines.reactor.awaitSingle
import kr.jay.elasticsearchprac.config.extension.toLocalDate
import kr.jay.elasticsearchprac.model.History
import kr.jay.elasticsearchprac.model.PgStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Component
import kotlin.reflect.KProperty

/**
 * HistoryNativeRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/10/24
 */
@Component
class HistoryNativeRepository(
    private val template: ReactiveElasticsearchTemplate,
) {

    suspend fun search(request: QrySearch): ResSearch {
        val criteria = Criteria().apply {
            request.orderId?.let {
                and(
//                Criteria(History::orderId.name).`in`(it)
                    History::orderId.criteria.`in`(it)
                )
            }
            request.userId?.let {
                and(
//                Criteria(History::userId.name).`in`(it)
                    History::userId.criteria.`in`(it)
                )
            }
            request.keyword?.split(" ")?.toSet()?.forEach {
                and(
                    History::description.criteria.contains(it)
                )
            }
            request.pgStatus?.let {
                and(
                    History::pgStatus.criteria.`in`(it)
                )
            }
            request.fromDt?.toLocalDate()?.atStartOfDay()?.let {
                and(
                    History::createdAt.criteria.greaterThanEqual(it)
                )
            }
            request.toDt?.toLocalDate()?.atStartOfDay()?.let {
                and(
                    History::createdAt.criteria.lessThan(it)
                )
            }
            request.fromAmount?.let {
                and(
                    History::amount.criteria.greaterThanEqual(it)
                )
            }
            request.toAmount?.let {
                and(
                    History::amount.criteria.lessThanEqual(it)
                )
            }
        }

        val query = CriteriaQuery(criteria, PageRequest.of(0, request.pageSize)).apply {
//            sort = Sort.by(Sort.Direction.DESC, History::createdAt.name)
            sort = History::createdAt.sort(Sort.Direction.DESC)
            searchAfter = request.pageNext
        }

        return template.searchForPage(query, History::class.java).awaitSingle().let { res ->
            ResSearch(
                res.content.map { it.content },
                res.totalElements,
                res.content.lastOrNull()?.sortValues
            )
        }
    }
}

val KProperty<*>.criteria: Criteria
    get() {
        return Criteria(this.name)
    }

fun KProperty<*>.sort(direction: Sort.Direction = Sort.Direction.ASC) = Sort.by(direction, this.name)

class QrySearch(
    val orderId: List<Long>?,
    val userId: List<Long>?,
    val keyword: String?,
    val pgStatus: List<PgStatus>?,
    val fromDt: String?,
    val toDt: String?,
    val fromAmount: Long?,
    val toAmount: Long?,
    val pageSize: Int = 10,
    val pageNext: List<Long>? = null,
)

data class ResSearch(
    val items: List<History>,
    val total: Long,
    val pageNext: List<Any>?,
)