package kr.jay.couponcore.component

import kr.jay.couponcore.model.event.CouponIssueCompleteEvent
import kr.jay.couponcore.service.CouponCacheService
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

/**
 * CouponEventListener
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/9/24
 */
@Component
class CouponEventListener(
    private val couponCacheService: CouponCacheService
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun issueComplete(event: CouponIssueCompleteEvent) {
        couponCacheService.putCouponCache(event.couponId)
        couponCacheService.putCouponLocalCache(event.couponId)
    }
}