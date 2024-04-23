package kr.jay.payment.config.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * LocalDateExtension
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/2/24
 */

fun String.toLocalDate(format: String = "yyyyMMdd"): LocalDate{
    return LocalDate.parse(this. filter { it.isDigit() }, DateTimeFormatter.ofPattern(format))
}

fun LocalDate.toString(format: String = "yyyyMMdd"): String{
    return this.format(DateTimeFormatter.ofPattern(format))
}