package kr.jay.couponapi.service

import kr.jay.couponapi.controller.dto.CouponIssueRequest
import kr.jay.couponcore.service.CouponIssueService
import org.springframework.stereotype.Service

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
){

    fun issueRequestV1(request: CouponIssueRequest){
        couponIssueService.issue(request.couponId, request.userId)
    }
}