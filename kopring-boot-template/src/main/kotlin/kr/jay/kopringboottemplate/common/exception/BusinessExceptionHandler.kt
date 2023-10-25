package kr.jay.kopringboottemplate.common.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * BusinessExceptionHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/26/23
 */
@RestControllerAdvice
class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException::class)
    protected fun handle(request: HttpServletRequest?, e: BusinessException): ResponseEntity<String> {
        return ResponseEntity.status(e.errorCodes.status).body(e.message)
    }
}