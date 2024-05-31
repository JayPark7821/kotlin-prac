package kr.jay.paymentservice.payment.adapter.out.web.product.client

import kr.jay.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

/**
 * ProductClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
interface ProductClient {

    fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}