package kr.jay.paymentservice.payment.domain

/**
 * PSPConfirmationStatus
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
enum class PSPConfirmationStatus(description:String) {
    DONE("결제 완료"),
    CANCELED("결제 취소"),
    EXPIRED("결제 만료"),
    PARTIAL_CANCELED("부분 취소"),
    ABORTED("결제 승인이 실패된 상태"),
    WAITING_FOR_DEPOSIT("입금 대기중 가상계좌"),
    IN_PROGRESS("결제 수단 정보와 해당 결제 수단의 소유자가 맞는지 인증을 마친 상태"),
    READY("결제 준비 완료 상태 초기상태"),;

    companion object {
        fun get(status: String): PSPConfirmationStatus {
            return PSPConfirmationStatus.entries.find{ it.name == status } ?: error("Invalid PSPConfirmationStatus: $status")

        }
    }
}