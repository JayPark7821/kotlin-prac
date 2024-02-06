package kr.jay.couponcore.service

import kr.jay.couponcore.repository.redis.RedisRepository
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

    fun availableTotalIssueQuantity(totalQuantity: Int?, couponId: Long): Boolean {
        if (totalQuantity == null) return true
        return totalQuantity > redisRepository.sCard(getIssueRequestKey(couponId))
    }


    fun availableUserIssueQuantity(couponId: Long, userId: Long): Boolean =
        !redisRepository.sIsMember(getIssueRequestKey(couponId), userId.toString())
}