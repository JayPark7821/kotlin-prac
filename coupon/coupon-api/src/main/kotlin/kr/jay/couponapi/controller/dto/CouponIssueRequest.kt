package kr.jay.couponapi.controller.dto

/**
 * CouponIssueRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */
data class CouponIssueRequest(
    val couponId: Long,
    val userId: Long
)
