package kr.jay.paymentservice.payment.application.port.out

import reactor.core.publisher.Mono

/**
 * PaymentValidationPort
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
interface PaymentValidationPort {
    fun isValid(orderId: String, amount:Long): Mono<Boolean>
}