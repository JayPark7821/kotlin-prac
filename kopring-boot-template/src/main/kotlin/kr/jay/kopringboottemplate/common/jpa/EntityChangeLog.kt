package kr.jay.kopringboottemplate.common.jpa

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import org.springframework.data.jpa.domain.support.AuditingEntityListener

/**
 * AuditLog
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */

@Entity
@EntityListeners(AuditingEntityListener::class)
class EntityChangeLog (
    log: String,
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Lob
    var log: String = log
        private set
}