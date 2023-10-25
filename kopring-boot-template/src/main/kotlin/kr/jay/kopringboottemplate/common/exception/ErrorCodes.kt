package kr.jay.kopringboottemplate.common.exception

import org.springframework.http.HttpStatus

/**
 * ErrorCodes
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */
enum class ErrorCodes(
    val status: HttpStatus,
    val message: String,
) {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
}