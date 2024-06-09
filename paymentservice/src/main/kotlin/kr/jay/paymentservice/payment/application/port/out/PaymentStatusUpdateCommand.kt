package kr.jay.paymentservice.payment.application.port.out

import kr.jay.paymentservice.payment.domain.PaymentExecutionFailure
import kr.jay.paymentservice.payment.domain.PaymentExtraDetails
import kr.jay.paymentservice.payment.domain.PaymentStatus

/**
 * PaymentStatusUpdateCommand
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/9/24
 */
data class PaymentStatusUpdateCommand(
    val paymentKey: String,
    val orderId: String,
    val status: PaymentStatus,
    val extraDetails: PaymentExtraDetails? = null,
    val failure: PaymentExecutionFailure? = null
){
    init {
        require(status == PaymentStatus.SUCCESS || status == PaymentStatus.FAILURE || status == PaymentStatus.UNKNOWN) {
            "결제 상태 $status 는 올바르지 않은 결제 상태입니다."
        }
        if(status == PaymentStatus.SUCCESS) {
            requireNotNull(extraDetails) {
                "결제 상태가 성공일 경우 extraDetails 는 필수입니다."
            }
        }else if (status == PaymentStatus.FAILURE) {
            requireNotNull(failure) {
                "결제 상태가 실패일 경우 failure 는 필수입니다."
            }
        }
    }
}
