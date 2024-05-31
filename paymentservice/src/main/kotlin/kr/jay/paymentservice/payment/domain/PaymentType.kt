package kr.jay.paymentservice.payment.domain

/**
 * PaymentType
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
enum class PaymentType(description: String) {
    NORMAL("일반결제"),;

    companion object {
        fun get(type: String): PaymentType {
            return entries.find{ it.name == type } ?: throw IllegalArgumentException("Invalid PaymentType: $type")

        }
    }

}