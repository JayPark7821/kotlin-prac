package kr.jay.kafkaprac.config

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import reactor.kafka.receiver.ReceiverOptions
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

/**
 * Consumer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/7/24
 */
private val logger = KotlinLogging.logger {}

@Service
class Consumer(
    private val properties: KafkaProperties,
    private val redisTemplate: ReactiveRedisTemplate<Any, Any>,
) {
    private val ops = redisTemplate.opsForValue()

    fun consume(topic: String, groupId: String, runner: (record: ConsumerRecord<String, String>) -> Unit) {
        properties.buildConsumerProperties(null).let { prop ->
            prop[ConsumerConfig.GROUP_ID_CONFIG] = groupId
            ReceiverOptions.create<String, String>(prop)
        }.subscription(listOf(topic))
            .let { option -> ReactiveKafkaConsumerTemplate(option) }
            .receiveAutoAck().flatMap { record ->
                val key = getKey(record, groupId)
                mono {
                    if (ops.setIfAbsent(key, true).awaitSingle()) {
                        redisTemplate.expire(key, 5.minutes.toJavaDuration())
                        runner.invoke(record)
                    }
                }
            }.subscribe()
    }

    fun getKey(record: ConsumerRecord<String, String>, groupId: String) =
        "kafka-consumer/${record.topic()}/${record.partition()}/${groupId}/${record.offset()}"
}