package kr.jay.paymentservice.payment.adapter.`in`.web.request

/**
 * TossPaymentConfirmRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
data class TossPaymentConfirmRequest(
    val paymentKey: String,
    val orderId: String,
    val amount: Long,
)
