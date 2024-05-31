package kr.jay.paymentservice.payment.domain

/**
 * CheckoutResult
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
data class CheckoutResult(
    val amount: Long,
    val orderId: String,
    val orderName: String,
)
