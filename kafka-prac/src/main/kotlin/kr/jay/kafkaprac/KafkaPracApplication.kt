package kr.jay.kafkaprac

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class KafkaPracApplication

fun main(args: Array<String>) {
    runApplication<KafkaPracApplication>(*args)
}
