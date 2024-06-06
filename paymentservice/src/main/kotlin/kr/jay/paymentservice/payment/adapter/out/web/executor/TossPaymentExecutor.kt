package kr.jay.paymentservice.payment.adapter.out.web.executor

import kr.jay.paymentservice.payment.adapter.out.web.response.TossPaymentConfirmationResponse
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.domain.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
) : PaymentExecutor {


    override fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
        return tossPaymentWebClient.post()
            .uri(uri)
            .header("idempotency-key", command.orderId)
            .bodyValue(
                """
                {
                    "paymentKey": "${command.paymentKey}",
                    "orderId": "${command.orderId}",
                    "amount": ${command.amount}
                }
            """.trimIndent()
            )
            .retrieve()
            .bodyToMono(TossPaymentConfirmationResponse::class.java)
            .map {
                PaymentExecutionResult(
                    paymentKey = command.paymentKey,
                    orderId = command.orderId,
                    extraDetails = PaymentExtraDetails(
                        type = PaymentType.get(it.type),
                        method = PaymentMethod.get(it.method),
                        approvedAt = LocalDateTime.parse(it.approvedAt, DateTimeFormatter.ISO_DATE_TIME),
                        pspRawData = it.toString(),
                        orderName = it.orderName,
                        pspConfirmationStatus = PSPConfirmationStatus.get(it.status),
                        totalAmount = it.totalAmount.toLong(),
                    ),
                    isSuccess = true,
                    isFailure = false,
                    isUnknown = false,
                    isRetryable = false
                )
            }
    }
}