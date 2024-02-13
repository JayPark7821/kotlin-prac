package kr.jay.errormonitoring.config.exception.sender

import jakarta.servlet.http.HttpServletRequest
import org.apache.el.util.ExceptionUtils
import org.slf4j.MDC
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors

/**
 * AsyncExceptionLogSender
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/14/24
 */
abstract class AsyncExceptionLogSender(
    private val exceptionNameFilter: ExceptionNameFilter,
) {
    fun send(ex: Exception, request: HttpServletRequest) {
        if (exceptionNameFilter.shouldNotSendException(ex)) {
            return
        }

        val exceptionMessage: String = generateExceptionMessage(ex, request)
        val exceptionTitle: String = generateExceptionTitle(ex, request)
        val mdc = MDC.getCopyOfContextMap()

        Executors.newFixedThreadPool(1).submit {
            try {
                if (mdc != null) {
                    MDC.setContextMap(mdc)
                    doSend(exceptionMessage, exceptionTitle)
                }
            } finally {
                MDC.clear()
            }
        }
    }

    protected abstract fun doSend(message: String?, tittle: String?)

    protected fun generateExceptionTitle(ex: java.lang.Exception, request: HttpServletRequest)=
        "EDC 에러 발생 [${request.method}] : $request.requestURI [Exception] : $ex.javaClass.simpleName"


    private fun generateExceptionMessage(ex: java.lang.Exception, request: HttpServletRequest): String {
        val errorMsgBuilder = StringBuilder()
        errorMsgBuilder.append("## 에러가 발생했습니다.")
            .append(System.lineSeparator())
            .append("* REQUEST : ")
            .append("[ ")
            .append(request.method)
            .append(" ] ")
            .append(request.requestURI)
            .append(System.lineSeparator())


        if (MDC.get("TRX_ID") != null) {
            errorMsgBuilder.append("* REQUEST-ID : ")
                .append(MDC.get("TRX_ID"))
                .append(System.lineSeparator())
        }

        errorMsgBuilder
            .append("* 발생시간 : ")
            .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .append(System.lineSeparator())
            .append("---")
            .append(System.lineSeparator())
            .append("```")
            .append(System.lineSeparator())
            .append(ex.javaClass)
            .append(" - ")
            .append(ex.message)
            .append(System.lineSeparator())
            .append(ex.stackTrace)
            .append("```")
            .append(System.lineSeparator())

        return errorMsgBuilder.toString()
    }
}