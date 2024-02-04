package kr.jay.couponcore.exception

/**
 * ErrorCode
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
enum class ErrorCode(
    val message: String
) {
    INVALID_COUPON_ISSUE_QUANTITY("발급 가능한 수량을 초과합니다."),
    INVALID_COUPON_ISSUE_DATE("발급 가능한 날짜가 아닙니다."),
    COUPON_NOT_EXIST("존재하지 않는 쿠폰입니다."),
    DUPLICATED_COUPON_ISSUE("이미 발급된 쿠폰입니다."),
}