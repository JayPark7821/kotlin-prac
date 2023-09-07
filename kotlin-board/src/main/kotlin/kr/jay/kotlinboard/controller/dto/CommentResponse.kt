package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.CommentResponseDto

/**
 * CommentResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: String,
)



fun CommentResponseDto.toResponse() =
    CommentResponse(
        id = id,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
    )
