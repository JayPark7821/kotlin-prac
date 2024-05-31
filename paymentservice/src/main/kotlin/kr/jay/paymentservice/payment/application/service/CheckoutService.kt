package kr.jay.paymentservice.payment.application.service

import kr.jay.paymentservice.common.UseCase
import kr.jay.paymentservice.payment.application.port.`in`.CheckoutCommand
import kr.jay.paymentservice.payment.application.port.`in`.CheckoutUseCase
import kr.jay.paymentservice.payment.application.port.out.LoadProductPort
import kr.jay.paymentservice.payment.application.port.out.SavePaymentPort
import kr.jay.paymentservice.payment.domain.*
import reactor.core.publisher.Mono

/**
 * CheckoutService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
@UseCase
class CheckoutService(
    private val loadProductPort: LoadProductPort,
    private val savePaymentPort: SavePaymentPort
) : CheckoutUseCase {
    override fun checkout(command: CheckoutCommand): Mono<CheckoutResult> {
        return loadProductPort.getProducts(command.cartId, command.productIds)
            .collectList()
            .map { createPaymentEvent(command, it) }
            .flatMap { savePaymentPort.save(it).thenReturn(it) }
            .map { CheckoutResult(amount = it.totalAmount(), orderId= it.orderId, orderName = it.orderName) }
    }

    private fun createPaymentEvent(command: CheckoutCommand, products: List<Product>): PaymentEvent {
        return PaymentEvent(
            buyerId = command.buyerId,
            orderId = command.idempotencyKey,
            orderName = products.joinToString { it.name },
            paymentOrders = products.map {
                PaymentOrder(
                    sellerId = it.sellerId,
                    orderId = command.idempotencyKey,
                    productId = it.id,
                    amount = it.amount,
                    paymentStatus =  PaymentStatus.NOT_STARTED,
                    buyerId = command.buyerId,
                )
            },
        )
    }
}