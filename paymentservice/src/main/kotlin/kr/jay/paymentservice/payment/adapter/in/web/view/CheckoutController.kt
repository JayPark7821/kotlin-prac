package kr.jay.paymentservice.payment.adapter.`in`.web.view

import kr.jay.paymentservice.common.WebAdapter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

/**
 * CheckoutController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
@Controller
@WebAdapter
class CheckoutController {

    @GetMapping("/")
    fun checkout()=  Mono.just("checkout")
}