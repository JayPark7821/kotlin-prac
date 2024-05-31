package kr.jay.paymentservice.payment.adapter.out.web.product.client

import kr.jay.paymentservice.payment.domain.Product
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

/**
 * MockProductClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
@Component
class MockProductClient: ProductClient {
    override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
        return Flux.fromIterable(
            productIds.map{
                Product(
                    id = it,
                    amount = BigDecimal(it * 10000.0),
                    quantity = 2,
                    name = "Product $it",
                    sellerId = 1
                )
            }
        )
    }
}