package kr.jay.paymentservice.payment.adapter.out.persistent.repository

import kr.jay.paymentservice.payment.adapter.out.persistent.exception.PaymentValidationException
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.math.BigInteger

/**
 * R2DBCPaymentValidationRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 6/7/24
 */
@Repository
class R2DBCPaymentValidationRepository(
    private val databaseClient: DatabaseClient
) : PaymentValidationRepository{
    override fun isValid(orderId: String, amount: Long): Mono<Boolean> {
        return databaseClient.sql(SELECT_PAYMENT_TOTAL_AMOUNT_QUERY)
            .bind("orderId", orderId)
            .fetch()
            .first()
            .handle { row, sink ->
                if ((row["total_amount"] as BigInteger).toLong() == amount) {
                    sink.next(true)
                } else {
                    sink.error(PaymentValidationException("결제 (orderId: $orderId) 에서 금액 (amount: $amount)이 올바르지 않습니다.") )
                }
            }
    }

    companion object{
        val SELECT_PAYMENT_TOTAL_AMOUNT_QUERY = """
            SELECT SUM(amount) as total_mount
            FROM payment_orders
            WHERE order_id = :orderId
        """.trimIndent()
    }
}