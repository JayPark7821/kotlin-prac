package kr.jay.payment.controller

import kr.jay.payment.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * ViewController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/29/24
 */
@Controller
class ViewController(
    private val orderService: OrderService,
) {

    @GetMapping("/hello/{name}")
    suspend fun hello(@PathVariable("name") name: String, model: Model): String {
        model.addAttribute("pname", name)
        model.addAttribute("orders", orderService.get(1L).toResOrder())
        return "hello-world.html"
    }

    @GetMapping("/pay/{orderId}")
    suspend fun pay(@PathVariable("orderId") orderId: Long, model: Model): String {
        model.addAttribute("order", orderService.get(orderId))

        return "pay.html"
    }

    @GetMapping("/pay/success")
    suspend fun paySucceed(request: RequestPaySucceed): String {
        if (!orderService.authSucceed(request))
            return "pay-fail.html"

        orderService.capture(request)
        return "pay-success.html"
    }

    @GetMapping("/pay/fail")
    suspend fun payFailed(request: RequestPayFailed): String {
        orderService.authFailed(request)
        return "pay-fail.html"
    }
}

data class RequestPaySucceed(
    val paymentKey: String,
    val orderId: String,
    val amount: Long,
    val paymentType: TossPaymentType,
)

data class RequestPayFailed(
    val code: String,
    val message: String,
    val orderId: String
)

enum class TossPaymentType {
    NORMAL,
    BRANDPAY,
    KEYIN
}
