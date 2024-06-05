package kr.jay.paymentservice.payment.application.port.`in`

import kr.jay.paymentservice.payment.domain.PaymentConfirmationResult
import reactor.core.publisher.Mono

/**
 * PaymentConfirmUseCase
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/5/24
 */
interface PaymentConfirmUseCase {

    fun confirm(command: PaymentConfirmCommand) : Mono<PaymentConfirmationResult>
}