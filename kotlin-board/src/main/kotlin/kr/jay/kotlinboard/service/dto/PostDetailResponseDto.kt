package kr.jay.kotlinboard.service.dto

import kr.jay.kotlinboard.domain.Post

/**
 * PostDetailResponseDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/06
 */
data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
)

fun Post.toDetailResponseDto() =
    PostDetailResponseDto(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt.toString()
    )
