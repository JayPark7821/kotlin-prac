package kr.jay.couponcore.exception

/**
 * CouponIssueException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
class CouponIssueException(
    val errorCode: ErrorCode,
    message: String
) : RuntimeException() {

    override val message: String = message
        get() = "[%s} %s".format(errorCode, field)

}