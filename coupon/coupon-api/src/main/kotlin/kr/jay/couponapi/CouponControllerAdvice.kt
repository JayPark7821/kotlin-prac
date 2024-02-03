package kr.jay.couponapi

import kr.jay.couponapi.controller.dto.CouponIssueResponseDto
import kr.jay.couponcore.exception.CouponIssueException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * CouponControllerAdvice
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */

@RestControllerAdvice
class CouponControllerAdvice {

    @ExceptionHandler(CouponIssueException::class)
    fun couponIssueExceptionHandler(e: CouponIssueException)=
        CouponIssueResponseDto(false, e.errorCode.message)
}