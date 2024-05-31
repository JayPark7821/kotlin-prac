package kr.jay.paymentservice.payment.domain

import java.time.LocalDateTime

/**
 * PaymentEvent
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
data class PaymentEvent(
    val id:Long? = null,
    val buyerId: Long,
    val orderName: String,
    val orderId: String,
    val paymentKey: String? =null,
    val paymentType: PaymentType? =null,
    val paymentMethod: PaymentMethod? =null,
    val approvedAt: LocalDateTime? = null,
    val paymentOrders: List<PaymentOrder> = emptyList(),
    private var isPaymentDone: Boolean = false,
)