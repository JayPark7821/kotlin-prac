package kr.jay.paymentservice.payment.adapter.`in`.web.api

import kr.jay.paymentservice.common.WebAdapter
import kr.jay.paymentservice.payment.adapter.`in`.web.request.TossPaymentConfirmRequest
import kr.jay.paymentservice.payment.adapter.`in`.web.response.ApiResponse
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import kr.jay.paymentservice.payment.application.service.PaymentConfirmService
import kr.jay.paymentservice.payment.domain.PaymentConfirmationResult
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
    private val paymentConfirmUseCase: PaymentConfirmUseCase

    ) {

    @PostMapping("/v1/toss/confirm")
    fun confirm(@RequestBody request: TossPaymentConfirmRequest): Mono<ResponseEntity<ApiResponse<PaymentConfirmationResult>>> {
        val command = PaymentConfirmCommand(
            paymentKey = request.paymentKey,
            orderId = request.orderId,
            amount = request.amount
        )

        return paymentConfirmUseCase.confirm(command)
            .map { ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "", it)) }

    }
}