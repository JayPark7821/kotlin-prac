package kr.jay.kafkaprac

import kr.jay.kafkaprac.config.Consumer
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@EnableKafka
class KafkaPracApplication(
    private val consumer: Consumer,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        consumer.consume("test", "A") {
            logger.info { "A Consumed message: $it" }
        }
        consumer.consume("test", "B") {
            logger.info { "B Consumed message: $it" }
        }
    }

}

fun main(args: Array<String>) {
    runApplication<KafkaPracApplication>(*args)
}
