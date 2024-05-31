package kr.jay.paymentservice.payment.domain

import java.math.BigDecimal

/**
 * Product
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
data class Product(
    val id:Long,
    val amount: BigDecimal,
    val quantity: Int,
    val name: String,
    val sellerId: Long,
) {
}