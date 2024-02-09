package kr.jay.couponcore.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.couponcore.component.DistributeLockExecutor
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
class AsyncCouponIssueServiceV2(
    private val redisRepository: RedisRepository,
    private val couponCacheService: CouponCacheService
) {

    fun issue(couponId: Long, userId: Long) {
        val coupon = couponCacheService.getCouponLocalCache(couponId)
        coupon.checkIssuableCoupon()
        issueRequest(couponId, userId, coupon.totalQuantity)
    }

    fun issueRequest(couponId: Long, userId: Long, totalIssueQuantity: Int) {
        redisRepository.issueRequest(couponId, userId, totalIssueQuantity)
    }
}