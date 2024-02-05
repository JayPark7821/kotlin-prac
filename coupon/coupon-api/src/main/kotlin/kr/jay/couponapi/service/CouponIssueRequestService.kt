package kr.jay.couponapi.service

import kr.jay.couponapi.controller.dto.CouponIssueRequest
import kr.jay.couponcore.component.DistributeLockExecutor
import kr.jay.couponcore.service.CouponIssueService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * CouponIssueRequestService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */

@Service
class CouponIssueRequestService (
    private val couponIssueService: CouponIssueService,
    private val distributeLockExecutor: DistributeLockExecutor
){

    fun issueRequestV1(request: CouponIssueRequest){
        distributeLockExecutor.execute(
            "lock_${request.couponId}",
            10000,
            10000
        ) { couponIssueService.issue(request.couponId, request.userId) }
    }
}