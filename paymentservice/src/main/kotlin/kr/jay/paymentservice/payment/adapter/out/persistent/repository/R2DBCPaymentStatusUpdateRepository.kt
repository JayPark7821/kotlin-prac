package kr.jay.paymentservice.payment.adapter.out.persistent.repository

import kr.jay.paymentservice.payment.adapter.out.persistent.exception.PaymentAlreadyProcessedException
import kr.jay.paymentservice.payment.domain.PaymentStatus
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * R2DBCPaymentStatusUpdateRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/6/24
 */
@Repository
class R2DBCPaymentStatusUpdateRepository(
    private val databaseClient: DatabaseClient,
    private val transactionalOperator: TransactionalOperator,
) : PaymentStatusUpdateRepository {
    override fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String): Mono<Boolean> {
       return checkPreviousPaymentOrderStatus(orderId)
            .flatMap { insertPaymentHistory(it, PaymentStatus.EXECUTING, "PAYMENT_COMFIRMATION_STARTED") }
            .flatMap { updatePaymentOrderStatus(orderId, PaymentStatus.EXECUTING) }
            .flatMap { updatePaymentKey(orderId, paymentKey) }
            .`as`(transactionalOperator::transactional)
           .thenReturn(true)
    }

    private fun updatePaymentKey(orderId: String, paymentKey: String): Mono<Long> {
        return databaseClient.sql(UPDATE_PAYMENT_KEY_QUERY)
            .bind("orderId", orderId)
            .bind("paymentKey", paymentKey)
            .fetch()
            .rowsUpdated()
    }

    private fun updatePaymentOrderStatus(orderId: String, executing: PaymentStatus): Mono<Long> {
        return databaseClient.sql(UPDATE_PAYMENT_ORDER_STATUS_QUERY)
            .bind("orderId", orderId)
            .bind("status", executing)
            .fetch()
            .rowsUpdated()

    }

    private fun checkPreviousPaymentOrderStatus(orderId: String): Mono<MutableList<Pair<Long, String>>> {
        return selectPaymentOrderStatus(orderId)
            .handle { paymentOrder, sink ->
                when (paymentOrder.second) {
                    PaymentStatus.NOT_STARTED.name, PaymentStatus.UNKNOWN.name, PaymentStatus.EXECUTING.name -> (
                        sink.next(paymentOrder)
                        )

                    PaymentStatus.SUCCESS.name -> (
                        sink.error(
                            PaymentAlreadyProcessedException(
                                message = "이미 처리 성공한 경제 입니다.",
                                status = PaymentStatus.SUCCESS
                            )
                        )
                        )

                    PaymentStatus.FAILURE.name -> (
                        sink.error(
                            PaymentAlreadyProcessedException(
                                message = "이미 처리 실패한 경제 입니다.",
                                status = PaymentStatus.FAILURE
                            )
                        )
                        )
                }
            }
            .collectList()
    }

    private fun selectPaymentOrderStatus(orderId: String): Flux<Pair<Long, String>> {
        return databaseClient.sql(SELECT_PAYMENT_ORDERS_STATUS_QUERY)
            .bind("orderId", orderId)
            .fetch()
            .all()
            .map { Pair(it["id"] as Long, it["payment_order_status"] as String) }
    }

    private fun insertPaymentHistory(
        paymentOrderIdToStatus: List<Pair<Long, String>>,
        status: PaymentStatus,
        reason: String,
    ): Mono<Long> {
        if (paymentOrderIdToStatus.isEmpty()) return Mono.empty()
        val valueClauses = paymentOrderIdToStatus.joinToString(", ") {
            "( ${it.first}, `${it.second}`, `${status}`, `${reason}` )"
        }
        return databaseClient.sql(INSERT_PAYMENT_HISTORY_QUERY(valueClauses))
            .fetch()
            .rowsUpdated()
    }

    companion object {
        val SELECT_PAYMENT_ORDERS_STATUS_QUERY = """
            SELECT id, payment_order_status
            FROM payment_orders
            WHERE order_id = :orderId
        """.trimIndent()

        val INSERT_PAYMENT_HISTORY_QUERY = fun(valueClauses: String) = """
            INSERT INTO payment_order_history (payment_order_id, previous_status , new_status, reason)
            VALUES $valueClauses
        """.trimIndent()

        val UPDATE_PAYMENT_ORDER_STATUS_QUERY = """
            UPDATE payment_orders
            SET payment_order_status = :status, updated_at = CURRENT_TIMESTAMP
            WHERE order_id = :orderId
        """.trimIndent()

        val UPDATE_PAYMENT_KEY_QUERY = """
            UPDATE payment_events
            SET payment_key = :paymentKey
            WHERE order_id = :orderId
        """.trimIndent()
    }
}