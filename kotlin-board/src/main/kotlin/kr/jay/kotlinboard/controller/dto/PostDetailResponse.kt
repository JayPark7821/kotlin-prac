package kr.jay.kotlinboard.controller.dto

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
