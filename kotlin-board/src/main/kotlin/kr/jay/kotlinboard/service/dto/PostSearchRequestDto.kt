package kr.jay.kotlinboard.service.dto

/**
 * PostSearchRequestDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/06
 */
data class PostSearchRequestDto(
    val title: String? = null,
    val createdBy: String? = null,
)
