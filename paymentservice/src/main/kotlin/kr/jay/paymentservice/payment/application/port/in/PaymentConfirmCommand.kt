package kr.jay.paymentservice.payment.application.port.`in`

/**
 * PaymentConfirmCommand
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/5/24
 */
data class PaymentConfirmCommand(
    val paymentKey: String,
    val orderId: String,
    val amount: Long,
)
