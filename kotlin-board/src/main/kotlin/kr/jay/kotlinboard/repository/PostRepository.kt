package kr.jay.kotlinboard.repository

import kr.jay.kotlinboard.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

/**
 * PostRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
interface PostRepository :JpaRepository<Post, Long>{
}
