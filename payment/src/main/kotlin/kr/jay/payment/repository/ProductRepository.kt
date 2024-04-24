package kr.jay.payment.repository

import kr.jay.payment.model.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * ProductRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
@Repository
interface ProductRepository : CoroutineCrudRepository<Product, Long>{
}