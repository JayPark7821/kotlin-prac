package kr.jay.couponcore.utils

/**
 * CouponRedisUtils
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/6/24
 */
class CouponRedisUtils {

    companion object{
        fun getIssueRequestKey(couponId: Long) = "issue.request.couponId=$couponId"
        fun getIssueRequestQueueKey() = "issue.request"
    }
}