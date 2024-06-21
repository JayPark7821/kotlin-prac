package kr.jay.paymentservice.payment.adapter.out.persistent

import kr.jay.paymentservice.common.PersistenceAdapter
import kr.jay.paymentservice.payment.adapter.out.persistent.repository.PaymentRepository
import kr.jay.paymentservice.payment.adapter.out.persistent.repository.PaymentStatusUpdateRepository
import kr.jay.paymentservice.payment.adapter.out.persistent.repository.PaymentValidationRepository
import kr.jay.paymentservice.payment.application.port.out.*
import kr.jay.paymentservice.payment.domain.PaymentEvent
import kr.jay.paymentservice.payment.domain.PaymentStatus
import kr.jay.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux
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
    private val paymentRepository: PaymentRepository,
    private val paymentStatusUpdateRepository: PaymentStatusUpdateRepository,
    private val paymentValidationRepository: PaymentValidationRepository,
):SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, LoadPendingPaymentPort {
    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }

    override fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String): Mono<Boolean> {
        return paymentStatusUpdateRepository.updatePaymentStatusToExecuting(orderId, paymentKey)
    }

    override fun updatePaymentStatus(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return paymentStatusUpdateRepository.updatePaymentStatus(command)
    }

    override fun isValid(orderId: String, amount: Long): Mono<Boolean> {
        return paymentValidationRepository.isValid(orderId, amount)
    }

    override fun getPendingPayments(): Flux<PendingPaymentEvent> {
        return paymentRepository.getPendingPayments()
    }

}