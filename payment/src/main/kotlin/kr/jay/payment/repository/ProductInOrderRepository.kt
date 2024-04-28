package kr.jay.payment.repository

import kr.jay.payment.model.Product
import kr.jay.payment.model.ProductInOrder
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * ProductInOrderRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
@Repository
interface ProductInOrderRepository : CoroutineCrudRepository<ProductInOrder, Long>{
    suspend fun countByOrderId(orderId: Long): Long
    suspend fun findAllByOrderId(orderId: Long): List<ProductInOrder>
}