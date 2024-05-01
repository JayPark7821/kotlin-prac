package kr.jay.payment.repository

import kr.jay.payment.model.Order
import kr.jay.payment.model.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * OrderRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
@Repository
interface OrderRepository : CoroutineCrudRepository<Order, Long>{
    suspend fun findAllByUserIdOrderByCreatedAtDesc(userId: Long): List<Order>

    suspend fun findByPgOrderId(pgOrderId: String): Order?
}