package kr.jay.mongoprac.product

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.math.BigDecimal

/**
 * ProductRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */
interface ProductRepository: MongoRepository<Product, String> {

    fun findByNameIgnoreCase(name: String): List<Product>

    fun findByNameStartingWith(name: String): List<Product>

    fun findByNameEndingWith(name: String): List<Product>

    fun findByNameContainingIgnoreCase(name: String): List<Product>

    fun findByPriceLessThan(price: BigDecimal): List<Product>

    fun findByPriceGreaterThan(price: BigDecimal): List<Product>

    fun findByPriceBetween(fromPrice: BigDecimal, toPrice: BigDecimal): List<Product>

    fun findByNameContainingIgnoreCaseOrderByPrice(name: String): List<Product>

    fun findByNameContainingIgnoreCaseOrderByPrice(name: String, pageable: Pageable): List<Product>

}