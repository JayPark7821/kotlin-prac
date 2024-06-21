package kr.jay.paymentservice.payment.domain

/**
 * PendingPaymentEvent
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/21/24
 */
data class PendingPaymentEvent(
    val paymentEventId: Long,
    val paymentKey: String,
    val orderId: String,
    val pendingPaymentOrders: List<PendingPaymentOrder>
) {
    fun totalAmount(): Long {
        return pendingPaymentOrders.sumOf { it.amount }
    }
}
