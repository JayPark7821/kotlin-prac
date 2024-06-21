package kr.jay.paymentservice.payment.application.port.out

import kr.jay.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux

/**
 * LoadPendingPaymentPort
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/21/24
 */
interface LoadPendingPaymentPort {
    fun getPendingPayments(): Flux<PendingPaymentEvent>
}