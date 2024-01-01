package kr.jay.webfluxcoroutine.controller

import jakarta.validation.*
import jakarta.validation.constraints.*
import kr.jay.webfluxcoroutine.service.AdvancedService
import mu.KotlinLogging
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

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
    @field:Size(min = 1, max = 10)
    val id: String?,
    @field:NotNull
    @field:Positive(message = "age must be positive")
    @field:Max(100)
    val age: Int?,
    @field:DateString
    val birthday: String?,
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DataValidator::class])
annotation class DateString(
    val message: String = "not a valid data",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class DataValidator : ConstraintValidator<DateString, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        val text = value?.filter { it.isDigit() } ?: return true
        return runCatching {
            LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyyMMdd")).let {
                if (text != it.format(DateTimeFormatter.ofPattern("yyyyMMdd"))) null else true
            }
        }
            .getOrNull() != null
    }
}