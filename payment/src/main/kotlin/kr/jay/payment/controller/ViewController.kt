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

}