package kr.jay.couponcore.repository.redis

import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode

/**
 * CouponIssueRequestCode
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/8/24
 */
enum class CouponIssueRequestCode(
    val resultCode: Int
) {
    SUCCESS(1 ),
    DUPLICATED_COUPON_ISSUE(2 ),
    INVALID_COUPON_ISSUE_QUANTITY(3 );

    companion object {
        fun find(code: String): CouponIssueRequestCode{
            code.toIntOrNull()?.let {
                return entries.find { it.resultCode == code.toInt() }
                    ?: throw IllegalArgumentException("Invalid code")
            } ?: throw IllegalArgumentException("Invalid code")
        }

        fun checkRequestResult(code: CouponIssueRequestCode){
            when(code){
                SUCCESS -> return
                DUPLICATED_COUPON_ISSUE -> throw CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "이미 발급된 쿠폰이 존재합니다.")
                INVALID_COUPON_ISSUE_QUANTITY -> throw CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE, "쿠폰 발급 가능 수량이 모두 소진되었습니다.")
            }
        }
    }
}