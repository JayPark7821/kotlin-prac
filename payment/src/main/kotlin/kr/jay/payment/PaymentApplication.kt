package kr.jay.payment

import kotlinx.coroutines.runBlocking
import kr.jay.payment.service.PaymentService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentApplication(
    private val paymentService: PaymentService
): ApplicationRunner{
    override fun run(args: ApplicationArguments?) {
        runBlocking {
            paymentService.recaptureOnBoot()
        }
    }

}

fun main(args: Array<String>) {
    runApplication<PaymentApplication>(*args)
}
