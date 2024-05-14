package kr.jay.kafkaprac.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import kr.jay.kafkaprac.config.Consumer
import kr.jay.kafkaprac.config.TOPIC_PAYMENT
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration

/**
 * OrderConsumer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/13/24
 */
private val logger = KotlinLogging.logger {}

@Configuration
class OrderConsumer(
    private val consumer: Consumer,
    private val historyApi: HistoryApi,
    private val objectMapper: ObjectMapper,
) : InitializingBean {
    override fun afterPropertiesSet() {
        consumer.consume(TOPIC_PAYMENT, "es") { record ->
            toOrder(record).let { order ->
                logger.debug { ">> es: $order" }
                historyApi.save(order)
            }
        }

        var total = 0L
        consumer.consume(TOPIC_PAYMENT, "sum") { record ->
            toOrder(record).let { order ->
                logger.debug { ">> sum: $order" }
                if(order.pgStatus == PgStatus.CAPTURE_SUCCESS){
                    total +=order.amount
                    logger.debug { ">> total: $total" }
                }
            }
        }
    }
    private fun toOrder(record: ConsumerRecord<String, String>) =
        objectMapper.readValue(record.value(), Order::class.java)
}