package kr.jay.payment.service

import kotlinx.coroutines.flow.toList
import kr.jay.payment.common.Beans.Companion.beanOrderService
import kr.jay.payment.controller.RequestPayFailed
import kr.jay.payment.controller.RequestPaySucceed
import kr.jay.payment.exception.NoOrderFound
import kr.jay.payment.exception.NoProductFound
import kr.jay.payment.model.Order
import kr.jay.payment.model.PgStatus
import kr.jay.payment.model.ProductInOrder
import kr.jay.payment.repository.OrderRepository
import kr.jay.payment.repository.ProductInOrderRepository
import kr.jay.payment.repository.ProductRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
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
    private val productInOrderRepository: ProductInOrderRepository,
    private val productService: ProductService,
) {

    @Transactional
    suspend fun create(request: ReqCreateOrder): Order {
        val productIds = request.products.map { it.productId }.toSet()
//        val productsById = productRepository.findAllById(productIds).toList().associateBy { it.id }
        val productsById = request.products.map{productService.get(it.productId)}.filterNotNull().associateBy { it.id }
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

    suspend fun get(orderId: Long): Order {
        return orderRepository.findById(orderId) ?: throw NoOrderFound("id: $orderId")
    }

    suspend fun getAll(userId: Long): List<Order>{
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
    }

    suspend fun delete(orderId: Long){
        orderRepository.deleteById(orderId)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    suspend fun save(order: Order) {
        orderRepository.save(order)
    }

    suspend fun getOrderByPgOrderId(pgOrderId: String): Order {
        return orderRepository.findByPgOrderId(pgOrderId) ?: throw NoOrderFound("pgOrderId: $pgOrderId")
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