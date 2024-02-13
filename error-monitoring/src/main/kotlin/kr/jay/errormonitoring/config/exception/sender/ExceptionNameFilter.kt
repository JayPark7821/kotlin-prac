package kr.jay.errormonitoring.config.exception.sender

/**
 * ExceptionNameFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/14/24
 */
fun interface ExceptionNameFilter {
    fun shouldNotSendException(ex: Exception): Boolean
}