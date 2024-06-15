package kr.jay.paymentservice.payment.adapter.out.web.executor

import io.netty.handler.timeout.TimeoutException
import kr.jay.paymentservice.payment.adapter.out.web.exception.PSPConfirmationException
import kr.jay.paymentservice.payment.adapter.out.web.exception.TossPaymentError
import kr.jay.paymentservice.payment.adapter.out.web.response.TossFailureResponse
import kr.jay.paymentservice.payment.adapter.out.web.response.TossPaymentConfirmationResponse
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.domain.*
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
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
    private val uri: String = "/v1/payments/confirm"
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
            .onStatus({ statusCode: HttpStatusCode -> statusCode.is4xxClientError || statusCode.is5xxServerError }) { response ->
                response.bodyToMono(TossFailureResponse::class.java)
                    .flatMap {
                        val error = TossPaymentError.get(it.code)
                        Mono.error<PSPConfirmationException>(
                            PSPConfirmationException(
                                errorCode = it.code,
                                errorMessage = it.message,
                                isSuccess = error.isSuccess(),
                                isFailure = error.isFailure(),
                                isUnknown = error.isUnknown(),
                                isRetryableError = error.isRetryableError(),
                            )
                        )
                    }
            }
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
            .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)).jitter(0.1)
                .filter { (it is PSPConfirmationException && it.isRetryableError) || it is TimeoutException }
                .onRetryExhaustedThrow { _, retrySignal -> retrySignal.failure() }
            )
    }
}