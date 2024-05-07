package kr.jay.kafkaprac.consumer

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

/**
 * TestConsumer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/7/24
 */
private val logger = KotlinLogging.logger {}
@Service
class TestConsumer {

    @KafkaListener(topics = ["test"],)
    fun consume(message: String) {
        logger.info { "Consumed message: $message" }
    }
}