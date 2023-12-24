package kr.jay

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.math.sin

/**
 * ReactorPubSub
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/25/23
 */

private val logger = KotlinLogging.logger {}
private val single = Schedulers.newSingle("single")

fun main(){
    Flux.range(1, 12 )
        .doOnNext { logger.debug { " >> 1st request -> $it" } }
        .filter { it % 2 == 0 }
        .doOnNext { logger.debug { " >> 2nd request -> $it" } }
        .filter { it % 3 == 0 }
        .delayElements(Duration.ofMillis(10)) // parallel
        .doOnNext { logger.debug { " >> 3rd request -> $it" } }
        .filter { it % 4 == 0 }
        .publishOn(single)
        .doOnNext { logger.debug { " >> 4th request -> $it" } }
        .subscribeOn(single)
        .blockLast()

}