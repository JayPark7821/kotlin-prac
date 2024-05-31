package kr.jay.paymentservice.payment.domain

import java.math.BigDecimal

/**
 * PaymentOrder
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
data class PaymentOrder(
    val id: Long? = null,
    val paymentEventId: Long? =null,
    val sellerId: Long,
    val buyerId: Long,
    val productId: Long,
    val orderId: String,
    val amount: BigDecimal,
    val paymentStatus: PaymentStatus,
    private var isLedgerUpdated: Boolean = false,
    private var isWalletUpdated: Boolean = false,

) {
}