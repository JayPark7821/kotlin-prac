package kr.jay.payment.service

import kr.jay.payment.config.CacheKey
import kr.jay.payment.config.CacheManager
import kr.jay.payment.model.Product
import kr.jay.payment.repository.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.time.Duration.Companion.minutes

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
    private val cacheManager: CacheManager,
    @Value("\${spring.profiles.active:local}")
    private val profile:String
) {

    val CACHE_KEY = "${profile}/payment/product".also{ cacheManager.TTL[it] = 10.minutes}

    suspend fun get(prodId: Long): Product?{
        val key = CacheKey(CACHE_KEY, prodId)
        return cacheManager.get(key){
            productRepository.findById(prodId)
        }
    }
}