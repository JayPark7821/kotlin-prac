package kr.jay.payment.model

import au.com.console.kassava.kotlinToString
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime

/**
 * BaseEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
open class  BaseEntity(
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
): Serializable {

    override fun toString(): String {
        return kotlinToString(arrayOf(
            BaseEntity::createdAt,
            BaseEntity::updatedAt,
        ))
    }
}