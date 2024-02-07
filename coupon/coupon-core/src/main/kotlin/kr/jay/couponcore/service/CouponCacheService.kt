package kr.jay.couponcore.service

import kr.jay.couponcore.repository.redis.dto.CouponRedisEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * CouponCacheService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/7/24
 */
@Service
class CouponCacheService(
    private val couponIssueService: CouponIssueService
) {

    @Cacheable(cacheNames = ["coupon"], key = "#couponId")
    fun getCouponCache(couponId: Long) =
        CouponRedisEntity(couponIssueService.findCoupon(couponId))
}