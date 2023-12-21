package kr.jay.mongoprac.product

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * QueryMethodService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */

@Service
class QueryMethodService(
    private val repository: ProductRepository
) {

    fun searchByName(name: String): List<Product> {
        return repository.findByNameIgnoreCase(name)
    }

    fun searchByNameStartingWith(name: String): List<Product> {
        return repository.findByNameStartingWith(name)
    }

    fun searchByNameEndingWith(name: String): List<Product> {
        return repository.findByNameEndingWith(name)
    }

    fun searchByNameContaining(name: String): List<Product> {
        return repository.findByNameContainingIgnoreCase(name)
    }

    fun searchByPriceLt(price: BigDecimal): List<Product> {
        return repository.findByPriceLessThan(price)
    }

    fun searchByPriceGt(price: BigDecimal): List<Product> {
        return repository.findByPriceGreaterThan(price)
    }

    fun searchByPriceBetween(fromPrice: BigDecimal, toPrice: BigDecimal): List<Product> {
        return repository.findByPriceBetween(fromPrice, toPrice)
    }

    fun sortAscByField(name: String): List<Product> {
        return repository.findByNameContainingIgnoreCaseOrderByPrice(name)
    }

    fun sortAndPageByField(name: String): List<Product> {
        return repository.findByNameContainingIgnoreCaseOrderByPrice(
            name,
            PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, name))
        )
    }
}