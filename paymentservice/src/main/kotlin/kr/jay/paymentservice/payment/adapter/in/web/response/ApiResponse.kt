package kr.jay.paymentservice.payment.adapter.`in`.web.response

import org.springframework.http.HttpStatus

/**
 * ApiResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
data class ApiResponse<T>(
    val status: Int = 200,
    val message: String = "",
    val data: T? = null,
) {
    companion object {
        fun <T> with(httpStatus: HttpStatus, message: String, data: T): ApiResponse<T> {
            return ApiResponse(
                status = httpStatus.value(),
                message = message,
                data = data
            )
        }
    }
}
