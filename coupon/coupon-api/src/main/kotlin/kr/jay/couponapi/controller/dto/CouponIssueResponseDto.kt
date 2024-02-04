package kr.jay.couponapi.controller.dto

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * CouponIssueResponseDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
data class CouponIssueResponseDto(
    val isSuccessful: Boolean,
    val message: String?
)
