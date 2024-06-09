package kr.jay.paymentservice.payment.adapter.out.persistent.repository

import kr.jay.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import reactor.core.publisher.Mono

/**
 * PaymentStatusUpdateRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/6/24
 */
interface PaymentStatusUpdateRepository {
    fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String) : Mono<Boolean>
    fun updatePaymentStatus(command: PaymentStatusUpdateCommand) : Mono<Boolean>
}