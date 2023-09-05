package kr.jay.kotlinboard.controller.dto

import org.springframework.web.bind.annotation.RequestParam

/**
 * PostSearchRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
data class PostSearchRequest(
    @RequestParam
    val title: String?,
    @RequestParam
    val createdBy: String?,
)
