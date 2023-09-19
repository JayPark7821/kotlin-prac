package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.PostCreateRequestDto

/**
 * PostCreateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
    val tags: List<String> = emptyList(),
)

fun PostCreateRequest.toDto() =
    PostCreateRequestDto(
        title = title,
        content = content,
        createdBy = createdBy,
        tags = tags,
    )
