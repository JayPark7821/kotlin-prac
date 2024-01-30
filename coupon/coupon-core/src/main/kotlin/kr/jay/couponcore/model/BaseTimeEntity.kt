package kr.jay.couponcore.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * BaseTimeEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/30/24
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity {

    @CreatedDate
    var createdDate: LocalDateTime? = null

    @LastModifiedDate
    var updatedDate: LocalDateTime? = null
}