package kr.jay.kotlinboard.service.dto

import kr.jay.kotlinboard.controller.dto.CommentResponse
import kr.jay.kotlinboard.domain.Comment

/**
 * CommentResponseDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
data class CommentResponseDto(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: String,
)

fun Comment.toResponseDto() =
    CommentResponseDto(
        id = id,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt.toString(),
    )

