package kr.jay.kotlinboard.controller.dto

import kr.jay.kotlinboard.service.dto.PostSummaryResponseDto

/**
 * PostSummaryResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
data class PostSummaryResponse(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val tag: String? = null,
)

fun PostSummaryResponseDto.toResponse() =
    PostSummaryResponse(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt
    )
