package kr.jay.payment.service.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

/**
 * PaymentApi
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/5/24
 */
@Service
class PaymentApi(
    @Value("\${payment.self.domain}")
    private val domain: String,
) {

    private val client = WebClient.builder().baseUrl(domain)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun recapture(orderId: Long) {
        client.put().uri("/order/recapture/$orderId").retrieve()
    }

}