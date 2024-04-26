package kr.jay.payment.service

import kr.jay.payment.model.Product
import kr.jay.payment.repository.ProductRepository
import org.springframework.stereotype.Service

/**
 * ProductService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/26/24
 */
@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    suspend fun get(prodId: Long): Product?{
        return productRepository.findById(prodId)
    }
}