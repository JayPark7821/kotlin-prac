package kr.jay.payment.config.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kr.jay.payment.config.extension.toLocalDate
import kr.jay.payment.config.extension.toString
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

/**
 * DateValidator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/2/24
 */
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
            text.toLocalDate().let {
                if (text != it.toString("yyyyMMdd")) null else true
            }
        }.getOrNull() != null
    }
}