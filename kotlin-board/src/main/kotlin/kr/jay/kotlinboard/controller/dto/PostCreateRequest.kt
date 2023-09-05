package kr.jay.kotlinboard.controller.dto

import org.springframework.data.annotation.CreatedBy

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
)
