package kr.jay.webfluxcoroutine.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import kr.jay.webfluxcoroutine.service.AdvancedService
import mu.KotlinLogging
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * AdvancedController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/29/23
 */

private val logger = KotlinLogging.logger {}

@RestController
class AdvancedController(
    private val service: AdvancedService,
) {

    @GetMapping("/test/mdc")
    suspend fun testTxid() {
        logger.debug { "start MDC TxId" }
        service.mdc()
        logger.debug { "end MDC TxId" }
    }

    @GetMapping("/test/error")
    suspend fun error(@RequestBody @Valid reqErrorTest: ReqErrorTest) {
        logger.debug { "request: $reqErrorTest" }
//        throw RuntimeException("yahooooooo !! ")
    }
}

data class ReqErrorTest(
    @field:NotEmpty
    val id: String?,
    val age: Int?,
)