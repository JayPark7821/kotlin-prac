package kr.jay.kafkaprac.consumer

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDateTime

/**
 * HistoryApi
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/13/24
 */
@Service
class HistoryApi(
    @Value("\${api.history.domain}")
    private val domain: String
) {
    private val client = WebClient.builder().baseUrl(domain)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun save(order: Order){
        client.post().uri("/history")
            .bodyValue(order.toReqSaveHistory())
            .awaitExchange {  }
    }
}

data class Order(
    var id: Long = 0,
    var userId: Long,
    var description: String? = null,
    var amount: Long = 0,
    var pgOrderId: String? = null,
    var pgKey: String? = null,
    var pgStatus: PgStatus = PgStatus.CREATE,
    var pgRetryCount: Int = 0,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
){
    fun toReqSaveHistory(): ReqSaveHistory{
        return this.let{
            ReqSaveHistory(
                orderId = it.id,
                userId = it.userId,
                description = it.description,
                amount = it.amount,
                pgStatus = it.pgStatus,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
    }
}
enum class PgStatus {
    CREATE,
    AUTH_SUCCESS,
    AUTH_FAIL,
    AUTH_INVALID,
    CAPTURE_REQUEST,
    CAPTURE_RETRY,
    CAPTURE_SUCCESS,
    CAPTURE_FAIL,
}

data class ReqSaveHistory(
    var orderId: Long,
    var userId: Long?,
    var description: String?,
    var amount: Long?,
    var pgStatus: PgStatus?,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
)