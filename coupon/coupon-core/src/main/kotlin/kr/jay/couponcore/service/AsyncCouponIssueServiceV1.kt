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
    private val couponIssueService: CouponIssueService,
    private val redisRepository: RedisRepository,
    private val objectMapper: ObjectMapper,
    private val distributeLockExecutor: DistributeLockExecutor
) {

    fun issue(couponId: Long, userId: Long) {
        val coupon = couponIssueService.findCoupon(couponId)

        if (!coupon.availableIssueDate())
            throw CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE, "쿠폰 발급 가능 기간이 아닙니다.")

        distributeLockExecutor.execute(
            "lock_${couponId}", 3000, 3000
        ) {
            if (!couponIssueRedisService.availableTotalIssueQuantity(coupon.totalQuantity, couponId))
                throw CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "쿠폰 발급 가능 수량이 모두 소진되었습니다.")

            if (!couponIssueRedisService.availableUserIssueQuantity(couponId, userId))
                throw CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰이 존재합니다.")

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