package kr.jay

import kotlinx.coroutines.delay
import mu.KotlinLogging

/**
 * CoroutineSetup
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

private val logger = KotlinLogging.logger {}

suspend fun main(){
    logger.debug("start")
    delay(1000)
    logger.debug("end")
}