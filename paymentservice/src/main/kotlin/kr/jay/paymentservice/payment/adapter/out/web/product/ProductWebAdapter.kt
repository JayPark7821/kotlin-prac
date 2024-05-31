package kr.jay.paymentservice.payment.adapter.out.web.product

import kr.jay.paymentservice.common.WebAdapter
import kr.jay.paymentservice.payment.adapter.out.web.product.client.ProductClient
import kr.jay.paymentservice.payment.application.port.out.LoadProductPort
import kr.jay.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

/**
 * ProductWebAdapter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
@WebAdapter
class ProductWebAdapter(
    private val productClient: ProductClient
): LoadProductPort {
    override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
        return productClient.getProducts(cartId, productIds)
    }
}