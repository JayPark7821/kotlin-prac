package kr.jay

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

fun main() {
    Mono.just(1).map { it + 1 }.doOnNext {
        logger.debug { " >> from publisher -> ${it}" }
    }.log().subscribe()

    Flux.just(1, 3, 5, 7, 9).map { it + 1 }.log().subscribe()

    // flux loop
    Flux.range(1, 10).map { it * it }.log().subscribe()  // block

    Flux.range(1, 10).flatMap { Mono.just(it * it) }.log().subscribe() // non-block

    Mono.just(1).flux().log().subscribe()  // Mono to Flux
}