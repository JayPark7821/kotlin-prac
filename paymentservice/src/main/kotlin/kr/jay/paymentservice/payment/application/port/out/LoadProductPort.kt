package kr.jay.paymentservice.payment.application.port.out

import kr.jay.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

/**
 * LoadProductPort
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
interface LoadProductPort {

    fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}