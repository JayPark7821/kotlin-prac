package kr.jay.couponapi.service

import kr.jay.couponapi.controller.dto.CouponIssueRequest
import kr.jay.couponcore.component.DistributeLockExecutor
import kr.jay.couponcore.service.AsyncCouponIssueServiceV1
import kr.jay.couponcore.service.AsyncCouponIssueServiceV2
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
    private val asyncCouponIssueServiceV1: AsyncCouponIssueServiceV1,
    private val asyncCouponIssueServiceV2: AsyncCouponIssueServiceV2,
){

    fun issueRequestV1(request: CouponIssueRequest){
        couponIssueService.issue(request.couponId, request.userId)
    }

    fun asyncIssueRequestV1(request: CouponIssueRequest){
        asyncCouponIssueServiceV1.issue(request.couponId, request.userId)
    }
    fun asyncIssueRequestV2(request: CouponIssueRequest){
        asyncCouponIssueServiceV2.issue(request.couponId, request.userId)
    }
}