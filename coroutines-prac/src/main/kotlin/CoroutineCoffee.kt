package kr.jay

import kotlinx.coroutines.*
import mu.KotlinLogging
import kotlin.system.measureTimeMillis

/**
 * CoroutineCoffee
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

private val logger = KotlinLogging.logger {}
private val worker = newSingleThreadContext("employee")
fun main() {
    measureTimeMillis {
        runBlocking {
            repeat(100) {
                launch(worker) { makeCoffee() }
            }
        }
    }.also { logger.debug { "total time: $it ms" } }
}

private suspend fun makeCoffee() {
    coroutineScope {
        launch {
            grindCoffee()
            bewCoffee()
        }
        launch {
            boilMilk()
            foamMilk()
        }
    }
    mixCoffeeAndMilk()
}

private suspend fun grindCoffee() {
    logger.debug { "grind coffee" }
    delay(1000)
    logger.debug { " >>> gridded coffee" }
}

private suspend fun bewCoffee() {
    logger.debug { "brew coffee" }
    delay(1000)
    logger.debug { " >>> brewed coffee" }
}

private suspend fun boilMilk() {
    logger.debug { "boil milk" }
    delay(1000)
    logger.debug { " >>> boiled milk" }
}

private suspend fun foamMilk() {
    logger.debug { "foam milk" }
    delay(1000)
    logger.debug { " >>> foamed milk" }
}

private suspend fun mixCoffeeAndMilk() {
    logger.debug { "mix coffee and milk" }
    delay(1000)
    logger.debug { " >>> mixed coffee and milk" }
}