package kr.jay.mongoprac.product

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * SearchService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */
@Service
class SearchService(
    private val mongoTemplate: MongoTemplate
) {

    fun searchByName(name: String): List<Product> {
        val query = Query()
        query.addCriteria(Criteria.where("name").`is`(name))
        return mongoTemplate.find(query, Product::class.java)
    }

    fun searchByNameStartingWith(name: String): List<Product> {
        val query = Query()
        query.addCriteria(Criteria.where("name").regex("^$name"))
        return mongoTemplate.find(query, Product::class.java)
    }

    fun searchByNameEndingWith(name: String): List<Product> {
        val query = Query()
        query.addCriteria(Criteria.where("name").regex("$name$"))
        return mongoTemplate.find(query, Product::class.java)
    }

    fun searchByPriceLt(price: BigDecimal): List<Product> {
        val query = Query()
        query.addCriteria(Criteria.where("name").lt(price))
        return mongoTemplate.find(query, Product::class.java)
    }

    fun searchByPriceGt(price: BigDecimal): List<Product> {
        val query = Query()
        query.addCriteria(Criteria.where("name").gt(price))
        return mongoTemplate.find(query, Product::class.java)
    }

    fun sortAscByField(fieldName: String): List<Product> {
        val query = Query()
        query.with(Sort.by(Sort.Direction.ASC, fieldName))
        return mongoTemplate.find(query, Product::class.java)
    }

    fun sortAndPageByField(fieldName: String): List<Product> {
        val query = Query()
        val pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, fieldName))
        query.with(pageable)
        return mongoTemplate.find(query, Product::class.java)
    }
}