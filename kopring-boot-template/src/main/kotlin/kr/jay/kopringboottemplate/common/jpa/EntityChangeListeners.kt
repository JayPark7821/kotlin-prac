package kr.jay.kopringboottemplate.common.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.PostPersist
import kr.jay.kopringboottemplate.common.utils.ApplicationContextProvider
import kr.jay.kopringboottemplate.domain.Post
import kr.jay.kopringboottemplate.infrastructure.AuditLogJpaRepository
import org.springframework.stereotype.Component

/**
 * AuditingListeners
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */

class EntityChangeListeners{

    @PostPersist
    fun saveChangeLog(entity: Post) {
        println(entity.toString())
        val auditLogJpaRepository: AuditLogJpaRepository = ApplicationContextProvider.getApplicationContext().getBean(AuditLogJpaRepository::class.java)
        val objectMapper = ApplicationContextProvider.getApplicationContext().getBean(ObjectMapper::class.java)
        auditLogJpaRepository.save(EntityChangeLog(objectMapper.writeValueAsString(entity)))
    }
}