package kr.jay.webfluxcoroutine.service

import mu.KotlinLogging
import org.springframework.stereotype.Service

/**
 * AdvancedService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/29/23
 */
private val logger = KotlinLogging.logger {}

@Service
class AdvancedService {
    fun mdc(){
        logger.debug { "MDC service !!" }
    }
}