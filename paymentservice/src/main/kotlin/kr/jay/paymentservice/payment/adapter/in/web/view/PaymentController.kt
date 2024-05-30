package kr.jay.paymentservice.payment.adapter.`in`.web.view

import kr.jay.paymentservice.common.WebAdapter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

/**
 * PaymentController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
@Controller
@WebAdapter
class PaymentController {

    @GetMapping("/success")
    fun successPage(): Mono<String> {
        return Mono.just("success")
    }

    @GetMapping("fail")
    fun failPage(): Mono<String> {
        return Mono.just("fail")
    }



}