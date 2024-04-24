package kr.jay.payment.model

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

/**
 * Product
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
@Table("TB_PRODUCT")
class Product(
    @Id
    var id: Long = 0,
    var name: String = "",
    var price: Long = 0,
) : BaseEntity() {
    override fun equals(other: Any?): Boolean = kotlinEquals(other, arrayOf(Product::id))

    override fun hashCode(): Int = kotlinHashCode(arrayOf(Product::id))

    override fun toString(): String = kotlinToString(
        arrayOf(
            Product::id,
            Product::name,
            Product::price,
        ), superToString = { super.toString() })
}