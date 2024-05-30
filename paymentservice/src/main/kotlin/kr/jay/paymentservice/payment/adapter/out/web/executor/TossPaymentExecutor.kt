package kr.jay.paymentservice.payment.adapter.out.web.executor

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * TossPaymentExecutor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
@Component
class TossPaymentExecutor(
    private val tossPaymentWebClient: WebClient,
    private val uri: String = "/v1/payments/confirm",
) {

    fun execute(paymentKey: String, orderId: String, amount: String): Mono<String> {
        return tossPaymentWebClient.post()
            .uri(uri)
            .bodyValue(
                """
                {
                    "paymentKey": "$paymentKey",
                    "orderId": "$orderId",
                    "amount": $amount
                }
            """.trimIndent()
            )
            .retrieve()
            .bodyToMono(String::class.java)
    }
}