package kr.jay.errormonitoring.config.exception.sender

/**
 * ExceptionNameFilterImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/14/24
 */
class ExceptionNameFilterImpl(
    private val excludeExceptionNames: List<String>
) : ExceptionNameFilter {
    override fun shouldNotSendException(ex: Exception): Boolean {
        return excludeExceptionNames.any { exceptionName -> ex.javaClass.simpleName == exceptionName }
    }
}