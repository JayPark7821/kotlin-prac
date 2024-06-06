package kr.jay.paymentservice.payment.domain

/**
 * PaymentMethod
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
enum class PaymentMethod(description: String) {
    EASY_PAY("간편결제")
    ;


    companion object {
        fun get(method: String): PaymentMethod {
            return PaymentMethod.entries.find{ it.name == method } ?: error("Invalid PaymentMethod: $method")

        }
    }
}