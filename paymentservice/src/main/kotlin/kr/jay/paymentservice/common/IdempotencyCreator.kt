package kr.jay.paymentservice.common

import java.util.*

/**
 * IdempotencyCreator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/31/24
 */
object IdempotencyCreator {

    fun create(data: Any) =
        UUID.nameUUIDFromBytes(data.toString().toByteArray()).toString()

}