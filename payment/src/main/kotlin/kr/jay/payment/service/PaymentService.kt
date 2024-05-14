package kr.jay.payment.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.payment.common.KafkaProducer
import kr.jay.payment.controller.RequestPayFailed
import kr.jay.payment.controller.RequestPaySucceed
import kr.jay.payment.controller.TossPaymentType
import kr.jay.payment.exception.InvalidOrderStatus
import kr.jay.payment.model.Order
import kr.jay.payment.model.PgStatus.*
import kr.jay.payment.service.api.PaymentApi
import kr.jay.payment.service.api.TossPayApi
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.time.Duration
import java.time.LocalDateTime

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
    private val objectMapper: ObjectMapper,
    private val paymentApi: PaymentApi,
    private val captureMarker: CaptureMarker,
    private val kafkaProducer: KafkaProducer,
) {

    @Transactional
    suspend fun authSucceed(request: RequestPaySucceed): Boolean {
        val order = orderService.getOrderByPgOrderId(request.orderId).apply {
            pgKey = request.paymentKey
            pgStatus = AUTH_SUCCESS
        }

        try {
            return if (order.amount != request.amount) {
                order.pgStatus = AUTH_INVALID
                false
            } else {
                true
            }
        } finally {
            orderService.save(order)
        }
    }

    @Transactional
    suspend fun authFailed(request: RequestPayFailed) {
        val order = orderService.getOrderByPgOrderId(request.orderId)
        if (order.pgStatus == CREATE) {
            order.pgStatus = AUTH_FAIL
            orderService.save(order)
        }

        logger.error {
            """
            >> Fail on error
             - request: $request
             - order: $order
        """.trimIndent()
        }

    }

    @Transactional
    suspend fun capture(order: Order) {
        if (order.pgStatus !in setOf(CAPTURE_REQUEST, CAPTURE_RETRY)) {
            throw InvalidOrderStatus("Invalid order: ${order.id} ${order.pgStatus}")
        }
        order.increaseRetryCount()

        captureMarker.put(order.id)

        try {
            tossPayApi.confirm(order.toReqPaySucceed())
            order.pgStatus = CAPTURE_SUCCESS
        } catch (e: Exception) {
            order.pgStatus = when (e) {
                is WebClientRequestException -> CAPTURE_RETRY
                is WebClientResponseException -> {
                    val resError = e.toToosPayApiError()
                    logger.error { ">>> res error: $resError" }

                    when (resError.code) {
                        "ALREADY_PROCESSED_PAYMENT" -> CAPTURE_SUCCESS
                        "PROVIDER_ERROR", "FAILED_INTERNAL_SYSTEM_PROCESSING" -> CAPTURE_RETRY
                        else -> CAPTURE_FAIL
                    }
                }

                else -> CAPTURE_FAIL
            }
            if (order.pgStatus == CAPTURE_RETRY && order.pgRetryCount >= 3)
                order.pgStatus = CAPTURE_FAIL
            if (order.pgStatus != CAPTURE_SUCCESS)
                throw e
        } finally {
            orderService.save(order)
            captureMarker.remove(order.id)
            if (order.pgStatus == CAPTURE_RETRY) {
                paymentApi.recapture(order.id)
            }
            kafkaProducer.sendPayment(order)
        }
    }

    @Transactional
    suspend fun capture(request: RequestPaySucceed) {
        val order = orderService.getOrderByPgOrderId(request.orderId).apply {
            pgStatus = CAPTURE_REQUEST
            orderService.save(this)
        }
        capture(order)
    }

    suspend fun recaptureOnBoot() {
        val now = LocalDateTime.now()
        captureMarker.getAll()
            .filter { Duration.between(it.updatedAt, now).seconds >= 60 }
            .also { if (it.isEmpty()) return }
            .forEach {
                captureMarker.remove(it.id)
                paymentApi.recapture(it.id)
            }
    }

    private fun Order.toReqPaySucceed() =
        this.let {
            RequestPaySucceed(
                paymentKey = it.pgKey!!,
                orderId = it.pgOrderId!!,
                amount = it.amount,
                paymentType = TossPaymentType.NORMAL
            )
        }

    private fun WebClientResponseException.toToosPayApiError(): ToosPayApiError {
        val json = String(this.responseBodyAsByteArray)
        return objectMapper.readValue(json, ToosPayApiError::class.java)
    }
}

data class ToosPayApiError(
    val code: String,
    val message: String,
)

