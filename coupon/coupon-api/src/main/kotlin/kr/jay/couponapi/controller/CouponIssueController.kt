package kr.jay.couponapi.controller

import kr.jay.couponapi.controller.dto.CouponIssueRequest
import kr.jay.couponapi.controller.dto.CouponIssueResponseDto
import kr.jay.couponapi.service.CouponIssueRequestService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * CouponIssueController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */

@RestController
class CouponIssueController(
    private val couponIssueRequestService: CouponIssueRequestService,
) {

    @PostMapping("/v1/issue")
    fun issueV1(@RequestBody request: CouponIssueRequest): CouponIssueResponseDto{
        couponIssueRequestService.issueRequestV1(request)
        return CouponIssueResponseDto(true, null)
    }
}