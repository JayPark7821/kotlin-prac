package kr.jay.payment.controller

import kotlinx.coroutines.delay
import kr.jay.payment.common.Beans.Companion.beanProductInOrderRepository
import kr.jay.payment.common.Beans.Companion.beanProductService
import kr.jay.payment.model.Order
import kr.jay.payment.model.PgStatus
import kr.jay.payment.service.*
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * OrderController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/28/24
 */
private val logger = KotlinLogging.logger {}
@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
    private val orderHistoryService: OrderHistoryService,
    private val paymentService: PaymentService,
) {

    @GetMapping("/{orderId}")
    suspend fun get(@PathVariable("orderId") orderId: Long) = orderService.get(orderId).toResOrder()

    @GetMapping("/all/{userId}")
    suspend fun getAll(@PathVariable("userId") userId: Long) = orderService.getAll(userId).map { it.toResOrder() }

    @PostMapping("/create")
    suspend fun create(@RequestBody request: ReqCreateOrder) = orderService.create(request).toResOrder()

    @DeleteMapping("/{orderId}")
    suspend fun delete(@PathVariable("orderId") orderId: Long) = orderService.delete(orderId)

    @GetMapping("/history")
    suspend fun delete(@RequestBody request: QryOrderHistory) = orderHistoryService.getHistories(request)

    @PutMapping("/recapture/{orderId}")
    suspend fun recapture(@PathVariable("orderId") orderId: Long) {
        orderService.get(orderId).let { order ->
            delay(getBackOffDelay(order).also {
                logger.debug { ">> delay: $it" }
            })
            paymentService.capture(order)
        }
    }

    private fun getBackOffDelay(order:Order): Duration {
        val temp = (2.0).pow(order.pgRetryCount).toInt() * 1000
        val delay = temp + (0..temp).random()
        return delay.milliseconds
    }
}

suspend fun Order.toResOrder() {
    return this.let {
        ResOrder(
            id = it.id,
            userId = it.userId,
            description = it.description,
            amount = it.amount,
            pgOrderId = it.pgOrderId,
            pgKey = it.pgKey,
            pgStatus = it.pgStatus,
            pgRetryCount = it.pgRetryCount,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            products = beanProductInOrderRepository.findAllByOrderId(it.id).map { prodInOrder ->
                ResProductQuantity(
                    id = prodInOrder.productId,
                    name = beanProductService.get(prodInOrder.productId)?.name ?: "unknown",
                    price = prodInOrder.price,
                    quantity = prodInOrder.quantity
                )
            }
        )
    }
}


data class ResOrder(
    val id: Long = 0,
    val userId: Long,
    val description: String? = null,
    val amount: Long = 0,
    val pgOrderId: String? = null,
    val pgKey: String? = null,
    val pgStatus: PgStatus = PgStatus.CREATE,
    val pgRetryCount: Int = 0,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val products: List<ResProductQuantity>,
)

data class ResProductQuantity(
    val id: Long,
    val name: String,
    val price: Long,
    val quantity: Int,
)
