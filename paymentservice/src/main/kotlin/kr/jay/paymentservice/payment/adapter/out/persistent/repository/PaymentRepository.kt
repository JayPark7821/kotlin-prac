package kr.jay.paymentservice.payment.adapter.out.persistent.repository

import kr.jay.paymentservice.payment.domain.PaymentEvent
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
}