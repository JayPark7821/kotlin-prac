package kr.jay.mongoprac.product

import kr.jay.mongoprac.category.Category
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * Product
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */

@Document
class Product(
    name: String,
    description: String,
    price: BigDecimal,
    quantity: Double,
    rating: Double,
    tags: List<String>,
    category: Category,
){

    constructor(name:String, description: String):this(name, description, BigDecimal.ZERO, 0.0, 0.0, listOf(), Category("",""))

    @Id
    var id: String? = null
    var name: String = name
        private set
    var price: BigDecimal = price
        private set
    var quantity: Double = quantity
        private set
    var rating: Double = rating
        private set
    var description: String = description
        private set
    var tags: List<String> = tags
        private set

    @DBRef
    var category: Category = category
        private set
}