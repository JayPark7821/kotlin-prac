package kr.jay.kafkapracproducer

import kr.jay.kafkapracproducer.produce.TestProducer
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class KafkaPracProducerApplication(
    private val producer: TestProducer,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        repeat(2) { i ->
            producer.send("test", "test message $i")
        }
    }

}

fun main(args: Array<String>) {
    runApplication<KafkaPracProducerApplication>(*args)
}
