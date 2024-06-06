package kr.jay.paymentservice.payment.adapter.out.web

import kr.jay.paymentservice.common.WebAdapter
import kr.jay.paymentservice.payment.adapter.out.web.executor.PaymentExecutor
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.application.port.out.PaymentExecutorPort
import kr.jay.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

/**
 * PaymentExecutorWebAdapter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
@WebAdapter
class PaymentExecutorWebAdapter(
    private val paymentExecutor: PaymentExecutor
) : PaymentExecutorPort{
    override fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
        return paymentExecutor.execute(command)
    }
}