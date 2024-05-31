package kr.jay.paymentservice.payment.adapter.out.persistent

import kr.jay.paymentservice.common.PersistenceAdapter
import kr.jay.paymentservice.payment.adapter.out.persistent.repository.PaymentRepository
import kr.jay.paymentservice.payment.application.port.out.SavePaymentPort
import kr.jay.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

/**
 * PaymentPersistentAdapter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
@PersistenceAdapter
class PaymentPersistentAdapter(
    private val paymentRepository: PaymentRepository
):SavePaymentPort {
    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }

}