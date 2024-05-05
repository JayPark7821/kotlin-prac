package kr.jay.payment.service.api

import kr.jay.payment.service.CaptureMarker
import kr.jay.payment.service.OrderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

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
    domain: String,
    private val captureMarker: CaptureMarker
) {

    private val client = WebClient.builder().baseUrl(domain)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun recapture(orderId: Long) {
        captureMarker.put(orderId)
        client.put().uri("/order/recapture/$orderId").retrieve()
            .bodyToMono<String>().subscribe()
    }

}