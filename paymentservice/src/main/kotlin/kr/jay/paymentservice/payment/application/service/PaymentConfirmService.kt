package kr.jay.paymentservice.payment.application.service

import kr.jay.paymentservice.common.UseCase
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import kr.jay.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import kr.jay.paymentservice.payment.domain.PaymentConfirmationResult
import reactor.core.publisher.Mono

/**
 * PaymentConfirmService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/5/24
 */
@UseCase
class PaymentConfirmService (
    private val paymentStatusUpdatePort: PaymentStatusUpdatePort
): PaymentConfirmUseCase {
    override fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmationResult> {
        paymentStatusUpdatePort.updatePaymentStatusToExecuting(command.orderId, command.paymentKey)
    }
}