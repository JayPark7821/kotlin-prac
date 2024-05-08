package kr.jay.kafkapracproducer.produce

import mu.KotlinLogging
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service
import reactor.kafka.sender.SenderOptions

/**
 * TestProducer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/7/24
 */
private val logger = KotlinLogging.logger {}

@Service
class TestProducer(
//    private val template: KafkaTemplate<String, String>
    private val template: ReactiveKafkaProducerTemplate<String, String>,
) {

    fun send(topic: String, message: String) {
        logger.info { "Producing message: $message" }
        template.send(topic, message)
    }
}

@Configuration
class ReactiveKafkaInitializer {
    @Bean
    fun reactiveProducer(properties: KafkaProperties): ReactiveKafkaProducerTemplate<String, String> {
        return properties.buildProducerProperties(null)
            .let { prop ->
                prop[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true
                SenderOptions.create<String, String>(prop)
            }
            .let { options -> ReactiveKafkaProducerTemplate(options) }
    }
}
