package kr.jay.paymentservice.payment.adapter.`in`.web.view

import kr.jay.paymentservice.common.IdempotencyCreator
import kr.jay.paymentservice.common.WebAdapter
import kr.jay.paymentservice.payment.adapter.`in`.web.request.CheckoutRequest
import kr.jay.paymentservice.payment.application.port.`in`.CheckoutCommand
import kr.jay.paymentservice.payment.application.port.`in`.CheckoutUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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
class CheckoutController(
    private val checkoutUseCase: CheckoutUseCase
) {

    @GetMapping("/")
    fun checkout(request: CheckoutRequest, model: Model): Mono<String>{
        val command = CheckoutCommand(
            cartId = request.cartId,
            buyerId = request.buyerId,
            productIds = request.productIds,
            idempotencyKey = IdempotencyCreator.create(request.seed)
        )

        return checkoutUseCase.checkout(command)
            .map{
                model.addAttribute("orderId", it.orderName)
                model.addAttribute("orderName", it.orderName)
                model.addAttribute("amount", it.amount)
                "checkout"
            }
    }
}