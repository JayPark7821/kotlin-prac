package kr.jay.paymentservice.payment.adapter.out.persistent.exception

import kr.jay.paymentservice.payment.domain.PaymentStatus

/**
 * PaymentAlreadyProcessedException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/6/24
 */
class PaymentAlreadyProcessedException(
    val status: PaymentStatus,
    message: String
): RuntimeException (message) {
}