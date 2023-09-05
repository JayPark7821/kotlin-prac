package kr.jay.kotlinboard.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * Post
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
@Entity
class Post (
    createdBy: String,
    title: String,
    content: String,
): BaseEntity(createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        protected set
    var content: String = content
        protected set
}
