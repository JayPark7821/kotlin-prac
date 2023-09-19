package kr.jay.kotlinboard.service.dto

/**
 * PostUpdateRequestDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/06
 */
data class PostUpdateRequestDto(
    val title: String,
    val content: String,
    val updatedBy: String,
    val tags: List<String> = emptyList(),
)
