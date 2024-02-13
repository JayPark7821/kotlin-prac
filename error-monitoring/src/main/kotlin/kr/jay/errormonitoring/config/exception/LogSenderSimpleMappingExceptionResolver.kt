package kr.jay.errormonitoring.config.exception

import jakarta.servlet.http.HttpServletRequest
import kr.jay.errormonitoring.config.exception.sender.AsyncExceptionLogSender
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver

/**
 * LogSenderSimpleMappingExceptionResolver
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/14/24
 */
class LogSenderSimpleMappingExceptionResolver(
    private val exceptionLogSender: AsyncExceptionLogSender,
) : SimpleMappingExceptionResolver() {

    internal fun logException(ex: Exception?, request: HttpServletRequest?) {
        if (ex != null && request != null)
            exceptionLogSender.send(ex, request)
    }

}