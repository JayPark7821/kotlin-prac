package kr.jay.dummywebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyWebfluxApplication

fun main(args: Array<String>) {
    runApplication<DummyWebfluxApplication>(*args)
}
