package kr.jay.paymentservice.payment.domain

/**
 * PendingPaymentOrder
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/21/24
 */
data class PendingPaymentOrder(
    val paymentOrderId: Long,
    val status: PaymentStatus,
    val amount: Long,
    val failedCount: Byte,
    val threshold: Byte,
)