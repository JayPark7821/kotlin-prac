package kr.jay.paymentservice.payment.application.port.out

import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

/**
 * PaymentExecutorPort
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
interface PaymentExecutorPort {
    fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult>
}