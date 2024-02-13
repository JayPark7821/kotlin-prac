package kr.jay.errormonitoring.config.exception

import kr.jay.errormonitoring.config.exception.sender.ExceptionNameFilter
import kr.jay.errormonitoring.config.exception.sender.ExceptionNameFilterImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * ExceptionLogSendFilterConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/14/24
 */
@Configuration
class ExceptionLogSendFilterConfig {

    @Bean
    fun exceptionNameFilter() =
        ExceptionNameFilterImpl(listOf("RuntimeException"))

}