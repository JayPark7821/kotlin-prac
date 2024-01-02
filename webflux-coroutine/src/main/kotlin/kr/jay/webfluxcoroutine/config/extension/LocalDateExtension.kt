package kr.jay.webfluxcoroutine.config.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * LocalDateExtension
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/2/24
 */

fun String.toLocalDate(format: String = "yyyy-MM-dd"): LocalDate{
    return LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
}

fun LocalDate.toString(format: String = "yyyyMMdd"): String{
    return this.format(DateTimeFormatter.ofPattern(format))
}