package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.CommentUpdateRequestDto

/**
 * CommentUpdateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/07
 */
data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String,
)

fun CommentUpdateRequest.toDto() =
    CommentUpdateRequestDto(
        content = this.content,
        updatedBy = this.updatedBy,
    )
