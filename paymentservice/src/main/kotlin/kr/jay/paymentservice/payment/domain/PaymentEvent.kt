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
){
    fun totalAmount(): Long {
        return paymentOrders.sumOf { it.amount }.toLong()
    }

    fun isPaymentDone() = isPaymentDone

    fun isSuccess(): Boolean {
        return paymentOrders.all { it.paymentStatus == PaymentStatus.SUCCESS }
    }

    fun isFailure(): Boolean {
        return paymentOrders.all { it.paymentStatus == PaymentStatus.FAILURE }
    }

    fun isUnknown(): Boolean {
        return paymentOrders.all { it.paymentStatus == PaymentStatus.UNKNOWN }
    }
}

