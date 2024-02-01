package kr.jay.couponcore.exception

/**
 * ErrorCode
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
enum class ErrorCode {
    INVALID_COUPON_ISSUE_QUANTITY,
    INVALID_COUPON_ISSUE_DATE,
    COUPON_NOT_EXIST,
    DUPLICATED_COUPON_ISSUE,
}