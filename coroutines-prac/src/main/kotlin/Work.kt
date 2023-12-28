package kr.jay

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

/**
 * Work
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

private val logger = KotlinLogging.logger {}

fun main() {
    runBlocking {
        val taskHard = launch {
            workHard()
        }
        val taskEasy = launch {
            workEasy()
        }
        delay(3000)
        taskHard.cancel()
        logger.debug(" end !!")
    }
}
private suspend fun workEasy(){
    logger.debug { "start work easy" }
    delay(1000)
    logger.debug { " >>> work easy done" }

}
private suspend fun workHard(){
    logger.debug { "start work hard" }
    try{
        while(true){
            delay(1000)
        }
    }finally {
        logger.debug { " >>> work hard done" }
    }

}