package kr.jay.kopringboottemplate.infrastructure

import kr.jay.kopringboottemplate.common.jpa.EntityChangeLog
import org.springframework.data.jpa.repository.JpaRepository

/**
 * AuditLogJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */
interface AuditLogJpaRepository : JpaRepository<EntityChangeLog, Long> {
}