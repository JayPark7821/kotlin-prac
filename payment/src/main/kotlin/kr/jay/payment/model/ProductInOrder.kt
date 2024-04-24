package kr.jay.payment.model

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.databind.ser.Serializers.Base
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

/**
 * ProductInOrder
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
@Table("TB_PRODUCT_IN_ORDER")
class ProductInOrder(
    var orderId: Long, // pk
    var productId: Long, // pk
    var price: Long,
    var quantity: Int,
    @Id
    var seq: Long = 0,
):BaseEntity() {
    override fun equals(other: Any?): Boolean = kotlinEquals(other, arrayOf(
        ProductInOrder::orderId,
        ProductInOrder::productId,
        ))

    override fun hashCode(): Int = kotlinHashCode(arrayOf(
        ProductInOrder::orderId,
        ProductInOrder::productId,
    ))

    override fun toString(): String = kotlinToString(
        arrayOf(
            ProductInOrder::orderId,
            ProductInOrder::productId,
            ProductInOrder::price,
            ProductInOrder::quantity,
        ), superToString = { super.toString() })
}