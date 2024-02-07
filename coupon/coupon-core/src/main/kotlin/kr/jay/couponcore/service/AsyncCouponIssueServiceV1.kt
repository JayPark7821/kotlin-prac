package kr.jay.couponcore.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.couponcore.component.DistributeLockExecutor
import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import kr.jay.couponcore.repository.redis.RedisRepository
import kr.jay.couponcore.repository.redis.dto.CouponIssueRequest
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestKey
import kr.jay.couponcore.utils.CouponRedisUtils.Companion.getIssueRequestQueueKey
import org.springframework.stereotype.Service

/**
 * AsyncCouponIssueServiceV1
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/6/24
 */
@Service
class AsyncCouponIssueServiceV1(
    private val couponIssueRedisService: CouponIssueRedisService,
    private val redisRepository: RedisRepository,
    private val objectMapper: ObjectMapper,
    private val distributeLockExecutor: DistributeLockExecutor,
    private val couponCacheService: CouponCacheService
) {

    fun issue(couponId: Long, userId: Long) {
        val coupon = couponCacheService.getCouponCache(couponId)
        coupon.checkIssuableCoupon()

        distributeLockExecutor.execute(
            "lock_${couponId}", 3000, 3000
        ) {
            couponIssueRedisService.checkCouponIssueQuantity(coupon, userId)
            issueRequest(couponId, userId)
        }
    }

    fun issueRequest(couponId: Long, userId: Long) {
        redisRepository.sAdd(getIssueRequestKey(couponId), userId.toString())
        redisRepository.rPush(
            getIssueRequestQueueKey(),
            objectMapper.writeValueAsString(CouponIssueRequest(couponId, userId))
        )
    }
}