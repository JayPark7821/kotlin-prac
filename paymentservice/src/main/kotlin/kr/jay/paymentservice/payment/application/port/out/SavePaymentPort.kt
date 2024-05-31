package kr.jay.paymentservice.payment.application.port.out

import kr.jay.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

/**
 * SavePaymentPort
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
interface SavePaymentPort {
    fun save(paymentEvent: PaymentEvent): Mono<Void>
}