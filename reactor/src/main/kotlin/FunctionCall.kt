package kr.jay

import mu.KotlinLogging
import reactor.core.publisher.Mono

/**
 * FunctionCall
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/25/23
 */

private val logger = KotlinLogging.logger {}
fun getRequest(): Mono<Int> {
    return Mono.just(1)
}

fun subA(request: Mono<Int>): Mono<Int> {
    return request.map { it + 1 }
}

fun subB(request: Mono<Int>): Mono<Int> {
    return request.map { it + 2 }
}

fun main() {
    val request = getRequest()
        .doOnNext { logger.debug { " >> request -> ${it}" } }
//    logger.debug { " >> request -> ${request.block()}" }

    val subA = subA(request)
        .doOnNext { logger.debug { " >> subA -> ${it}" } }

    val subB = subB(subA)
        .doOnNext { logger.debug { " >> subB -> ${it}" } }

    subB.subscribe()

    // ==================================

    getRequest()
        .doOnNext { logger.debug { " >> request -> ${it}" } }
        .flatMap { subA(Mono.just(it)) }
        .doOnNext { logger.debug { " >> subA -> ${it}" } }
        .flatMap { subB(Mono.just(it)) }
        .doOnNext { logger.debug { " >> subB -> ${it}" } }
        .subscribe()


    getRequest()
        .doOnNext { logger.debug { " >> request -> ${it}" } }
        .flatMap { subA2(it) }
        .doOnNext { logger.debug { " >> subA -> ${it}" } }
        .flatMap { subB2(it) }
        .doOnNext { logger.debug { " >> subB -> ${it}" } }
        .subscribe()



}


fun subA2(request: Int): Mono<Int> {
    return Mono.fromCallable { request + 1 }
}

fun subB2(request: Int): Mono<Int> {
    return Mono.fromCallable { request + 2 }
}

