package kr.jay.paymentservice.payment.test

import kr.jay.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

interface PaymentDatabaseHelper {

  fun getPayments(orderId: String): PaymentEvent?

  fun clean(): Mono<Void>
}