package kr.jay.couponcore.service

import kr.jay.couponcore.exception.CouponIssueException
import kr.jay.couponcore.exception.ErrorCode
import kr.jay.couponcore.model.Coupon
import kr.jay.couponcore.model.CouponIssue
import kr.jay.couponcore.model.event.CouponIssueCompleteEvent
import kr.jay.couponcore.repository.mysql.CouponIssueJpaRepository
import kr.jay.couponcore.repository.mysql.CouponIssueRepository
import kr.jay.couponcore.repository.mysql.CouponJpaRepository
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * CouponIssueService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */

@Service
class CouponIssueService(
    private val couponJpaRepository: CouponJpaRepository,
    private val couponIssueJpaRepository: CouponIssueJpaRepository,
    private val couponIssueRepository: CouponIssueRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun issue(couponId: Long, userId: Long) {
        val coupon = findCouponWithLock(couponId)
            .apply { issue() }
        saveCouponIssue(coupon.id!!, userId)
        publishCouponIssueEvent(coupon)
    }

    @Transactional(readOnly = true)
    fun findCoupon(couponId: Long) =
        couponJpaRepository.findByIdOrNull(couponId) ?: throw CouponIssueException(
            ErrorCode.COUPON_NOT_EXIST,
            "쿠폰 정책이 존재하지 않습니다. $couponId"
        )

    @Transactional
    fun findCouponWithLock(couponId: Long) =
        couponJpaRepository.findCouponWithLock(couponId) ?: throw CouponIssueException(
            ErrorCode.COUPON_NOT_EXIST,
            "쿠폰 정책이 존재하지 않습니다. $couponId"
        )

    @Transactional
    fun saveCouponIssue(couponId: Long, userId: Long): CouponIssue {
        checkAlreadyIssued(couponId, userId)
        return CouponIssue(couponId, userId)
            .let { couponIssueJpaRepository.save(it) }
    }

    private fun checkAlreadyIssued(couponId: Long, userId: Long) {
        couponIssueRepository.findFirstCouponIssued(couponId, userId)
            ?.let {
                throw CouponIssueException(
                    ErrorCode.DUPLICATED_COUPON_ISSUE,
                    "이미 발급된 쿠폰입니다. couponId: $couponId, userId: $userId"
                )
            }
    }

    private fun publishCouponIssueEvent(coupon: Coupon) {
        if(coupon.isIssueComplete()){
            eventPublisher.publishEvent(CouponIssueCompleteEvent(coupon.id!!))
        }
    }
}