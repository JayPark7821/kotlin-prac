package kr.jay.kotlinboard.repository

import kr.jay.kotlinboard.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

/**
 * TageRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/08
 */
interface TageRepository : JpaRepository<Tag, Long>{
    fun findByPostId(postId: Long): List<Tag>
}

