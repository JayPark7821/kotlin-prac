package kr.jay.kopringboottemplate.infrastructure

import kr.jay.kopringboottemplate.domain.Users
import org.springframework.data.jpa.repository.JpaRepository

/**
 * UserJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */
interface UserJpaRepository : JpaRepository<Users, Long> {
}