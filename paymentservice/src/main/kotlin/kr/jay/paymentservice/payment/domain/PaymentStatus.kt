package kr.jay.paymentservice.payment.domain

/**
 * PaymentStatus
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
enum class PaymentStatus(description: String) {
    NOT_STARTED("결제 승인 시작 전"),
    EXECUTING("결제 진행 중"),
    SUCCESS("결제 성공"),
    FAILURE("결제 실패"),
    UNKNOWN("알 수 없음"),

}