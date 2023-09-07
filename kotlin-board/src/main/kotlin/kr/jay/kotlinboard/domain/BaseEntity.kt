package kr.jay.kotlinboard.domain

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

/**
 * BaseEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */

@MappedSuperclass
abstract class BaseEntity(
    createdBy: String,
) {
    val createdBy: String = createdBy
    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedBy: String? = null
        private set
    var updatedAt: LocalDateTime? = null
        private set

    protected fun updatedBy(updatedBy: String) {
        this.updatedBy = updatedBy
        this.updatedAt = LocalDateTime.now()
    }
}
