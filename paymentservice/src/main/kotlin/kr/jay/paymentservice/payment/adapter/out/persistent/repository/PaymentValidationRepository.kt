package kr.jay.paymentservice.payment.adapter.out.persistent.repository

import reactor.core.publisher.Mono

/**
 * PaymentValidationRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
interface PaymentValidationRepository {

    fun isValid(orderId: String, amount:Long): Mono<Boolean>
}