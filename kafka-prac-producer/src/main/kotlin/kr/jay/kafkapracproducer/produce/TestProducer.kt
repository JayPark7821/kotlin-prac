

package kr.jay.kafkapracproducer.produce

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

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
    private val template: KafkaTemplate<String, String>
) {

    fun send(topic:String, message:String) {
        logger.info { "Producing message: $message" }
        template.send(topic, message)
    }
}
