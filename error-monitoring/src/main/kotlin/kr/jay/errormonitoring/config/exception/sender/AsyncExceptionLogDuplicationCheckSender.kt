package kr.jay.errormonitoring.config.exception.sender

import jakarta.servlet.http.HttpServletRequest

/**
 * AsyncExceptionLogDuplicationCheckSender
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/15/24
 */
abstract class AsyncExceptionLogDuplicationCheckSender :AsyncExceptionLogSender(ExceptionNameFilter { false }){
     fun send(ex: Exception?, request: HttpServletRequest?) {
        if (!isDuplicated(ex, request)) {
            super.send(ex!!, request!!)
        }
    }

    protected abstract fun isDuplicated(ex: Exception?, request: HttpServletRequest?): Boolean
}