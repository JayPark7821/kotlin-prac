package kr.jay.payment.model

/**
 * PgStatus
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/23/24
 */
enum class PgStatus {
    CREATE,
    AUTH_SUCCESS,
    AUTH_FAIL,
    AUTH_INVALID,
    CAPTURE_REQUEST,
    CAPTURE_RETRY,
    CAPTURE_SUCCESS,
    CAPTURE_FAIL,
}