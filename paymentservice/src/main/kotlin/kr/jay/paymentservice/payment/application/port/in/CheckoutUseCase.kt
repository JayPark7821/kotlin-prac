package kr.jay.paymentservice.payment.application.port.`in`

import kr.jay.paymentservice.payment.domain.CheckoutResult
import reactor.core.publisher.Mono

/**
 * CheckoutUseCase
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
interface CheckoutUseCase {

    fun checkout(command: CheckoutCommand): Mono<CheckoutResult>
}