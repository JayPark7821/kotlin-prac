package kr.jay.kotlinboard.service.dto

import kr.jay.kotlinboard.domain.Post

/**
 * PostCreateRequestDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun PostCreateRequestDto.toEntity() = Post(
    title = title,
    content = content,
    createdBy = createdBy,
)
