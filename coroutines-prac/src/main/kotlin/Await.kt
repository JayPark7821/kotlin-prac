package kr.jay

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging
import kotlin.time.Duration.Companion.seconds

/**
 * Await
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

private val logger = KotlinLogging.logger {}

private suspend fun downloadA(): Int {
    repeat(1){
        logger.debug { "downloadA" }
        delay(1.seconds)
    }

    return 1
}

private suspend fun downloadB(): Int {
    repeat(3){
        logger.debug { "downloadA" }
        delay(1.seconds)
    }

    return 2
}

private suspend fun downloadC(): Int {
    repeat(5){
        logger.debug { "downloadC" }
        delay(1.seconds)
    }
    return 3
}

suspend fun main(){
//    coroutineScope {
//        downloadA()
//        downloadB()
//        downloadC()
//    }

//    coroutineScope {
//        launch { downloadA() }
//        launch { downloadB() }
//        launch { downloadC() }
//        logger.debug { ">>> end coroutineScope" }
//    }
//    logger.debug { ">> end finally" }
//
    coroutineScope {
        val taskA = async { downloadA() }
        val taskB = async { downloadB() }
        val taskC = async { downloadC() }
        val total = taskA.await() + taskB.await() + taskC.await()
        logger.debug { ">>> end coroutineScope $total" }
    }
    logger.debug { ">> end finally" }
}

