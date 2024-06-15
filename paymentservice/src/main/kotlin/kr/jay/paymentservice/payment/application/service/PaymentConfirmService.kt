package kr.jay.paymentservice.payment.application.service

import io.netty.handler.timeout.TimeoutException
import kr.jay.paymentservice.common.UseCase
import kr.jay.paymentservice.payment.adapter.out.persistent.exception.PaymentAlreadyProcessedException
import kr.jay.paymentservice.payment.adapter.out.persistent.exception.PaymentValidationException
import kr.jay.paymentservice.payment.adapter.out.web.exception.PSPConfirmationException
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import kr.jay.paymentservice.payment.application.port.out.PaymentExecutorPort
import kr.jay.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import kr.jay.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import kr.jay.paymentservice.payment.application.port.out.PaymentValidationPort
import kr.jay.paymentservice.payment.domain.PaymentConfirmationResult
import kr.jay.paymentservice.payment.domain.PaymentFailure
import kr.jay.paymentservice.payment.domain.PaymentStatus
import reactor.core.publisher.Mono

/**
 * PaymentConfirmService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/5/24
 */
@UseCase
class PaymentConfirmService(
    private val paymentStatusUpdatePort: PaymentStatusUpdatePort,
    private val paymentValidationPort: PaymentValidationPort,
    private val paymentExecutorPort: PaymentExecutorPort,
    private val paymentErrorHandler: PaymentErrorHandler
) : PaymentConfirmUseCase {
    override fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmationResult> {
        return paymentStatusUpdatePort.updatePaymentStatusToExecuting(command.orderId, command.paymentKey)
            .filterWhen { paymentValidationPort.isValid(command.orderId, command.amount) }
            .flatMap { paymentExecutorPort.execute(command) }
            .flatMap {
                paymentStatusUpdatePort.updatePaymentStatus(
                    command = PaymentStatusUpdateCommand(
                        paymentKey = it.paymentKey,
                        orderId = it.orderId,
                        status = it.paymentStatus(),
                        extraDetails = it.extraDetails,
                        failure = it.failure
                    )
                ).thenReturn(it)
            }
            .map { PaymentConfirmationResult(status = it.paymentStatus(), failure = it.failure) }
            .onErrorResume { paymentErrorHandler.handlePaymentConfirmationError(it, command) }
    }
}