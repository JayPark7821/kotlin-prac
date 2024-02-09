package kr.jay.couponcore.service

import kr.jay.couponcore.repository.redis.dto.CouponRedisEntity
import org.springframework.aop.framework.AopContext
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

    @Cacheable(cacheManager = "localCacheManager", cacheNames = ["coupon"], key = "#couponId")
    fun getCouponLocalCache(couponId: Long) =
        proxy().getCouponCache(couponId)

    private fun proxy() = AopContext.currentProxy() as CouponCacheService

}