package kr.jay.mongoprac.product

import org.springframework.stereotype.Service

/**
 * ProductService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */
@Service
class ProductService(
    private val repository: ProductRepository
) {

    fun save(product: Product) :String {
        return repository.save(product).id!!
    }

    fun findById(id: String) : Product {
        return repository.findById(id).orElseThrow()
    }

    fun findAll() : List<Product> {
        return repository.findAll()
    }

    fun delete(id: String) {
        repository.deleteById(id)
    }
}