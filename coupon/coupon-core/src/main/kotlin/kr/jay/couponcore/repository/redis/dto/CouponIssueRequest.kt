package kr.jay.couponcore.repository.redis.dto

/**
 * CouponIssueRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/6/24
 */
data class CouponIssueRequest(
    val couponId: Long,
    val userId: Long
)
