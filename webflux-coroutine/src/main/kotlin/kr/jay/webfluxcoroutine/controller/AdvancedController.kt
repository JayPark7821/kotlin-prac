package kr.jay.webfluxcoroutine.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.*
import kr.jay.webfluxcoroutine.config.validator.DateString
import kr.jay.webfluxcoroutine.exception.ExternalApi
import kr.jay.webfluxcoroutine.exception.InvalidParameter
import kr.jay.webfluxcoroutine.service.AccountService
import kr.jay.webfluxcoroutine.service.AdvancedService
import kr.jay.webfluxcoroutine.service.ResAccount
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*

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
    private val externalApi: ExternalApi,
    private val accountService: AccountService,
) {

    @GetMapping("/test/mdc")
    suspend fun testTxid() {
        logger.debug { "start MDC TxId" }
        service.mdc()
        logger.debug { "end MDC TxId" }
    }

    @GetMapping("/test/error")
    suspend fun error(
        @RequestBody @Valid request: ReqErrorTest,
//        errors: BindingResult,
    ) {
        logger.debug { "request: $request" }

        if (request.message == "error") {
//            errors.rejectValue(request::message.name, "custom")
//            throw BindException(errors)
            throw InvalidParameter(request, request::message, code = "customCode", message = "custom error")
        }
//        throw RuntimeException("yahooooooo !! ")
    }

    @GetMapping("/external/delay")
    suspend fun delay() {
        externalApi.delay()
    }

    @GetMapping("/external/circuit/{flag}", "/external/circuit", "/external/circuit/")
    suspend fun testCircuitBreaker(@PathVariable flag: String): String {
        return externalApi.testCircuitBreaker(flag)
    }

    @GetMapping("/account/{id}")
    suspend fun getAccount(@PathVariable("id") id: Long) = accountService.get(id)

    @PutMapping("/account/{id}/{amount}")
    suspend fun deposit(@PathVariable("id") id: Long, @PathVariable("amount") amount: Long) : ResAccount{
        accountService.deposit(id, amount)
        return accountService.get(id)
    }
}


data class ReqErrorTest(
    @field:NotEmpty
    @field:Size(min = 1, max = 10)
    val id: String?,
    @field:NotNull
    @field:Positive(message = "age must be positive")
    @field:Max(100)
    val age: Int?,
    @field:DateString
    val birthday: String?,

    val message: String? = null,
)

