package kr.jay.paymentservice.payment.adapter.out.persistent.exception

/**
 * PaymentValidationException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
class PaymentValidationException(
    message: String
):RuntimeException(message) {
}