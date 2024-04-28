package kr.jay.payment.controller

import kr.jay.payment.service.OrderService
import kr.jay.payment.service.ReqCreateOrder
import org.springframework.web.bind.annotation.*

/**
 * OrderController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/28/24
 */
@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
) {

    @GetMapping("/{orderId}")
    suspend fun get(@PathVariable("orderId") orderId: Long) = orderService.get(orderId)

    @GetMapping("/all/{userId}")
    suspend fun getAll(@PathVariable("userId") userId: Long) = orderService.getAll(userId)

    @PostMapping("/create")
    suspend fun create(@RequestBody request: ReqCreateOrder) = orderService.create(request)

    @DeleteMapping("/{orderId}")
    suspend fun delete(@PathVariable("orderId") orderId: Long) = orderService.delete(orderId)
}
