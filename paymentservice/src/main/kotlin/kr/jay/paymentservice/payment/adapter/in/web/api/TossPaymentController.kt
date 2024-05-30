package kr.jay.paymentservice.payment.adapter.`in`.web.api

import kr.jay.paymentservice.common.WebAdapter
import kr.jay.paymentservice.payment.adapter.`in`.web.request.TossPaymentConfirmRequest
import kr.jay.paymentservice.payment.adapter.`in`.web.response.ApiResponse
import kr.jay.paymentservice.payment.adapter.out.web.executor.TossPaymentExecutor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * TossPaymentController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
@WebAdapter
@RestController
class TossPaymentController(
    private val tossPaymentExecutor: TossPaymentExecutor,
) {

    @PostMapping("/v1/toss/confirm")
    fun confirm(@RequestBody request: TossPaymentConfirmRequest): Mono<ResponseEntity<ApiResponse<String>>> {
        return tossPaymentExecutor.execute(
            paymentKey = request.paymentKey,
            orderId = request.orderId,
            amount = request.amount.toString()
        ).map {
            ResponseEntity.ok().body(
                ApiResponse.with(
                    HttpStatus.OK,
                    "ok",
                    it
                )
            )
        }
    }
}