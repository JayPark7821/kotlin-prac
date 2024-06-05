package kr.jay.paymentservice.payment.application.port.out

import reactor.core.publisher.Mono

/**
 * PaymentStatusUpdatePort
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/5/24
 */
interface PaymentStatusUpdatePort {

    fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String) : Mono<Boolean>
}