package kr.jay.errormonitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ErrorMonitoringApplication

fun main(args: Array<String>) {
    runApplication<ErrorMonitoringApplication>(*args)
}
