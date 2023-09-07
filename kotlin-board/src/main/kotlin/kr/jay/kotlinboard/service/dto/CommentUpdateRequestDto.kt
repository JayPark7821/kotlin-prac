package kr.jay.kotlinboard.service.dto

/**
 * CommentUpdateRequestDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
data class CommentUpdateRequestDto(
    val content: String,
    val updatedBy: String,
)
