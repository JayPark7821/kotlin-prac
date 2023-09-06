package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.PostDetailResponseDto

/**
 * PostDetailResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
)

fun PostDetailResponseDto.toResponse() =
    PostDetailResponse(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt
    )
