package kr.jay.kafkaprac.config

import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import reactor.kafka.receiver.ReceiverOptions

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
) {

    fun consume(topic: String, groupId: String) {
        properties.buildConsumerProperties().let { prop ->
            prop[ConsumerConfig.GROUP_ID_CONFIG] = groupId
            ReceiverOptions.create<String, String>(prop)
        }.let { option ->
            option.subscription(listOf(topic))
        }.let { option -> ReactiveKafkaConsumerTemplate(option) }
            .let { consumer ->
                consumer.receiveAutoAck().flatMap { record ->
                    mono {
                        logger.debug { record }
                    }
                }.subscribe()

            }
    }
}