package kr.jay.kotlinboard.service.dto

import kr.jay.kotlinboard.domain.Post

/**
 * PostSummaryResponseDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/06
 */
data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
)

fun Post.toSummaryResponseDto() =
    PostSummaryResponseDto(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt.toString()
    )
