package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.PostUpdateRequestDto

/**
 * PostUpdateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
)

fun PostUpdateRequest.toDto() =
    PostUpdateRequestDto(
        title = title,
        content = content,
        updatedBy = updatedBy
    )
