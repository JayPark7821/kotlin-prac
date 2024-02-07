package kr.jay.couponcore.service

import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import kr.jay.couponcore.repository.redis.RedisRepository
import kr.jay.couponcore.repository.redis.dto.CouponRedisEntity
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestKey
import org.springframework.stereotype.Service

/**
 * CouponIssueRedisService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/6/24
 */
@Service
class CouponIssueRedisService(
    private val redisRepository: RedisRepository
) {

    fun checkCouponIssueQuantity(couponRedisEntity: CouponRedisEntity, userId: Long) {
        if (!availableTotalIssueQuantity(couponRedisEntity.totalQuantity, couponRedisEntity.id))
            throw CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "쿠폰 발급 가능 수량이 모두 소진되었습니다.")

        if (!availableUserIssueQuantity(couponRedisEntity.id, userId))
            throw CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰이 존재합니다.")
    }

    fun availableTotalIssueQuantity(totalQuantity: Int?, couponId: Long): Boolean {
        if (totalQuantity == null) return true
        return totalQuantity > redisRepository.sCard(getIssueRequestKey(couponId))
    }


    fun availableUserIssueQuantity(couponId: Long, userId: Long): Boolean =
        !redisRepository.sIsMember(getIssueRequestKey(couponId), userId.toString())
}