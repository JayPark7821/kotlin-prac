package kr.jay.kotlinboard.repository

import kr.jay.kotlinboard.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

/**
 * CommentRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
interface CommentRepository: JpaRepository<Comment, Long>
