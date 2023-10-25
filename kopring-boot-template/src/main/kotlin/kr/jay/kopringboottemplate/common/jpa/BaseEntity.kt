package kr.jay.kopringboottemplate.common.jpa

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * BaseEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity{

//    @CreatedBy
//    @Column(updatable = false)
//    lateinit var createdBy: String

    @CreatedDate
    @Column(updatable = false)
    lateinit var createdDate: LocalDateTime

//    @LastModifiedBy
//    lateinit var updatedBy: String

    @LastModifiedDate
    lateinit var lastModifiedDate: LocalDateTime

    override fun toString(): String {
        return "BaseEntity(" +
//                "createdBy='$createdBy', " +
                "createdDate=$createdDate, " +
//                "updatedBy='$updatedBy', " +
                "lastModifiedDate=$lastModifiedDate" +
                ")"
    }


}
