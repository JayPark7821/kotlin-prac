package kr.jay.mongoprac.category

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * CategoryRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */
interface CategoryRepository : MongoRepository<Category, String> {
}