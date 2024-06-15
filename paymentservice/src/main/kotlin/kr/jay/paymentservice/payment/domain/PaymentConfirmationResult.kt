package kr.jay.paymentservice.payment.domain

/**
 * PaymentConfirmationResult
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/5/24
 */
data class PaymentConfirmationResult(
    val status: PaymentStatus,
    val failure: PaymentFailure? = null,
){
    init {
        if(status == PaymentStatus.FAILURE) {
            requireNotNull(failure) {
                "결제 상태가 실패일 경우 PaymentExecutionFailure 는 필수입니다."
            }
        }
    }

    val message = when (status){
        PaymentStatus.SUCCESS -> "결제가 성공적으로 완료되었습니다."
        PaymentStatus.FAILURE -> "결제가 실패하였습니다."
        PaymentStatus.UNKNOWN -> "결제 상태를 알 수 없습니다."
        else -> error("결제 상태가 올바르지 않습니다.")
    }
}
