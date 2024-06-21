package kr.jay.paymentservice.payment.adapter.out.persistent.repository

import kr.jay.paymentservice.payment.domain.PaymentEvent
import kr.jay.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * PaymentRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
interface PaymentRepository {
    fun save(paymentEvent: PaymentEvent): Mono<Void>

    fun getPendingPayments(): Flux<PendingPaymentEvent>
}