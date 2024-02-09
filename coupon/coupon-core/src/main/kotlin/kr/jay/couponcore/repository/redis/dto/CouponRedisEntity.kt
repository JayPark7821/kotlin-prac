package kr.jay.couponcore.repository.redis.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import kr.jay.couponcore.config.JacksonConfig
import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import kr.jay.couponcore.model.Coupon
import kr.jay.couponcore.model.CouponType
import java.time.LocalDateTime

/**
 * CouponRedisEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/7/24
 */
class CouponRedisEntity(
    val id: Long,
    val couponType: CouponType,
    val totalQuantity: Int,
    dateIssueStart: LocalDateTime,
    dateIssueEnd: LocalDateTime,
    val availableIssueQuantity: Boolean,
) {
    constructor(coupon: Coupon) :
        this(
            coupon.id!!,
            coupon.couponType,
            coupon.totalQuantity ?: Int.MAX_VALUE,
            coupon.dateIssueStart,
            coupon.dateIssueEnd,
            coupon.availableIssueQuantity()
        )


    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val dateIssueStart: LocalDateTime = dateIssueStart

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val dateIssueEnd: LocalDateTime = dateIssueEnd

    private fun availableIssueDate(): Boolean {
        val now = LocalDateTime.now()
        return now.isAfter(dateIssueStart) && now.isBefore(dateIssueEnd)
    }

    fun checkIssuableCoupon() {
        if(!availableIssueQuantity){
            throw CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "쿠폰 발급 가능 수량이 초과 되었습니다.")
        }

        if (!availableIssueDate())
            throw CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE, "쿠폰 발급 가능 기간이 아닙니다.")
    }
}