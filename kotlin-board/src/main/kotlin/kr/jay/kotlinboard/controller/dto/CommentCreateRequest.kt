package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.CommentCreateRequestDto
import org.springframework.data.annotation.CreatedBy

/**
 * CommentCreateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
data class CommentCreateRequest(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequest.toDto() =
    CommentCreateRequestDto(
        content = this.content,
        createdBy = this.createdBy,
    )
