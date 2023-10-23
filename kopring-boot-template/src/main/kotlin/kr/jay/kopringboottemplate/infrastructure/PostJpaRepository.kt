package kr.jay.kopringboottemplate.infrastructure

import kr.jay.kopringboottemplate.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * PostJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/23/23
 */
interface PostJpaRepository : JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.users.id = :userId")
    fun findByUserId(@Param("userId")id: Long): List<Post>
}