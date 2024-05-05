package kr.jay.payment.service

import kr.jay.payment.common.Beans
import kr.jay.payment.controller.RequestPayFailed
import kr.jay.payment.controller.RequestPaySucceed
import kr.jay.payment.exception.NoOrderFound
import kr.jay.payment.model.Order
import kr.jay.payment.model.PgStatus
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException

/**
 * PaymentService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/5/24
 */

private val logger = KotlinLogging.logger {}

@Service
class PaymentService(
    private val orderService: OrderService,
    private val tossPayApi: TossPayApi,

) {

    @Transactional
    suspend fun authSucceed(request: RequestPaySucceed): Boolean {
        val order = orderService.getOrderByPgOrderId(request.orderId).apply{
            pgKey = request.paymentKey
            pgStatus= PgStatus.AUTH_SUCCESS
        }

        try{
            return if(order.amount != request.amount){
                order.pgStatus = PgStatus.AUTH_INVALID
                false
            } else{
                true
            }
        } finally {
            orderService.save(order)
        }
    }

    @Transactional
    suspend fun authFailed(request: RequestPayFailed) {
        val order = orderService.getOrderByPgOrderId(request.orderId)
        if(order.pgStatus == PgStatus.CREATE){
            order.pgStatus = PgStatus.AUTH_FAIL
            orderService.save(order)
        }

        logger.error{"""
            >> Fail on error
             - request: $request
             - order: $order
        """.trimIndent()
        }

    }

    @Transactional
    suspend fun capture(request: RequestPaySucceed) : Boolean{
        val order = orderService.getOrderByPgOrderId(request.orderId). apply {
            pgStatus = PgStatus.CAPTURE_REQUEST
            Beans.beanOrderService.save(this)
        }

        return try{
            tossPayApi.confirm(request)
            order.pgStatus = PgStatus.CAPTURE_SUCCESS
            true
        } catch (e: Exception){
            order.pgStatus = when {
                e is WebClientRequestException -> PgStatus.CAPTURE_RETRY
                e is WebClientResponseException -> PgStatus.CAPTURE_FAIL
                else -> PgStatus.CAPTURE_FAIL
            }
            false
        } finally {
            Beans.beanOrderService.save(order)
        }
    }
}

