package kr.jay.paymentservice.payment.adapter.out.web.executor

import kr.jay.paymentservice.payment.adapter.out.web.exception.PSPConfirmationException
import kr.jay.paymentservice.payment.adapter.out.web.exception.TossPaymentError
import kr.jay.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import kr.jay.paymentservice.payment.test.PSPTestWebClientConfiguration
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.util.*
/**
 * TossPaymentExecutorTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/15/24
 */



@SpringBootTest
@Import(PSPTestWebClientConfiguration::class)
@Tag("TooLongTime")
class TossPaymentExecutorTest (
    @Autowired private val pspTestWebClientConfiguration: PSPTestWebClientConfiguration
){

    @Test
    fun `should handle correctly various TossPaymentError scenarios`() {
        generateErrorScenarios().forEach { errorScenario ->
            val command = PaymentConfirmCommand(
                paymentKey = UUID.randomUUID().toString(),
                orderId = UUID.randomUUID().toString(),
                amount = 10000L
            )

            val paymentExecutor = TossPaymentExecutor(
                tossPaymentWebClient = pspTestWebClientConfiguration.createTestTossWebClient(
                    Pair("TossPayments-Test-Code", errorScenario.errorCode)
                ),
                uri = "/v1/payments/key-in"
            )

            try {
                paymentExecutor.execute(command).block()
            } catch (e: PSPConfirmationException) {
                assertThat(e.isSuccess).isEqualTo(errorScenario.isSuccess)
                assertThat(e.isFailure).isEqualTo(errorScenario.isFailure)
                assertThat(e.isUnknown).isEqualTo(errorScenario.isUnknown)
            }
        }
    }

    private fun generateErrorScenarios(): List<ErrorScenario> {
        return TossPaymentError.entries.map { error ->
            ErrorScenario(
                errorCode = error.name,
                isSuccess = error.isSuccess(),
                isFailure = error.isFailure(),
                isUnknown = error.isUnknown()
            )
        }
    }
}

data class ErrorScenario(
    val errorCode: String,
    val isFailure: Boolean,
    val isUnknown: Boolean,
    val isSuccess: Boolean
)
