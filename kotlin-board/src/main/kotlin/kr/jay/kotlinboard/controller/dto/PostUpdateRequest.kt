package kr.jay.kotlinboard.controller.dto

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
