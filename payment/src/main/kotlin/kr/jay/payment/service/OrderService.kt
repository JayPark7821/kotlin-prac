package kr.jay.payment.service

import kotlinx.coroutines.flow.toList
import kr.jay.payment.exception.NoProductFound
import kr.jay.payment.model.Order
import kr.jay.payment.model.PgStatus
import kr.jay.payment.model.ProductInOrder
import kr.jay.payment.repository.OrderRepository
import kr.jay.payment.repository.ProductInOrderRepository
import kr.jay.payment.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * OrderService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/26/24
 */
@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val productInOrderRepository: ProductInOrderRepository,
) {

    @Transactional
    suspend fun create(request: ReqCreateOrder): Order {
        val productIds = request.products.map { it.productId }.toSet()
        val productsById = productRepository.findAllById(productIds).toList().associateBy { it.id }
        productIds.filter { !productsById.containsKey(it) }
            .let { remains ->
                if (remains.isNotEmpty()) {
                    throw NoProductFound("product ids: $remains")
                }
            }
        val amount = request.products.sumOf { productsById[it.productId]!!.price * it.quantity }

        val description =
            request.products.joinToString(", ") { "${productsById[it.productId]!!.name} X ${it.quantity}" }

        val newOrder = orderRepository.save(
            Order(
                userId = request.userId,
                amount = amount,
                description = description,
                pgOrderId = "${UUID.randomUUID()}".replace("-", ""),
                pgStatus = PgStatus.CREATE,
            )
        )

        request.products.forEach {
            productInOrderRepository.save(
                ProductInOrder(
                    orderId = newOrder.id,
                    productId = it.productId,
                    quantity = it.quantity,
                    price = productsById[it.productId]!!.price
                )
            )
        }
        return newOrder
    }
}

data class ReqCreateOrder(
    val userId: Long,
    val products: List<ReqProductQuantity>,
)

data class ReqProductQuantity(
    val productId: Long,
    val quantity: Int,
)