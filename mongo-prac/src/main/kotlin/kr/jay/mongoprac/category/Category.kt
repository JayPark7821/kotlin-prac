package kr.jay.mongoprac.category

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Category
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/21/23
 */

@Document
class Category(
    name: String,
    description: String,
) {

    @Id
    var id: String? = null
    var name: String = name
        private set
    var description: String = description
        private set
}