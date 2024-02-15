package kr.jay.errormonitoring.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.http.HttpMethod
import org.springframework.util.AntPathMatcher
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.math.min

/**
 * LoggingFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/15/24
 */

private val log = KotlinLogging.logger {}

class LoggingFilter : OncePerRequestFilter() {

    private val FORM_CONTENT_TYPE: String = "application/x-www-form-urlencoded"

    private val TRX_ID: String = "TRX_ID"

    private val TRX_TIME: String = "TRX_TIME"

    private val TRX_PAYLOAD: String = "TRX_PAYLOAD"

    private val NEW_LINE: String = System.lineSeparator()

    private val DEFAULT_MAX_PAYLOAD_LENGTH: Int = 68400

    private var maxPayloadLength: Int = 0

    private var excludeUris: List<String>? = null

    fun LoggingFilter() {
        this.excludeUris = ArrayList()
        this.maxPayloadLength = DEFAULT_MAX_PAYLOAD_LENGTH
    }

    fun LoggingFilter(excludeUris: List<String>?, maxPayloadLength: Int) {
        this.excludeUris = excludeUris
        this.maxPayloadLength = maxPayloadLength
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        MDC.put(TRX_ID, UUID.randomUUID().toString().substring(0, 8))
        MDC.put(TRX_TIME, System.currentTimeMillis().toString())

        val cachedRequest: ContentCachingRequestWrapper =
            ContentCachingRequestWrapper(request, this.maxPayloadLength)
        val cachedResponse: ContentCachingResponseWrapper =
            ContentCachingResponseWrapper(response)

        try {
            log.info(createLogFromRequest(cachedRequest))
            filterChain.doFilter(cachedRequest, cachedResponse)
        } finally {
            log.info(createLogFromResponse(cachedResponse))
            MDC.clear()
        }
    }

    @Throws(IOException::class)
    private fun createLogFromResponse(response: HttpServletResponse): String {
        val message: StringBuilder = StringBuilder()
        message.append("Response ")
            .append("  ")
            .append("[ Status ] ")
            .append(response.status)

        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper::class.java)?.copyBodyToResponse()
        message.append("  ")
            .append("[ Request End ] ")

        if (MDC.get(TRX_TIME) != null) {
            message.append(System.currentTimeMillis() - MDC.get(TRX_TIME).toLong())
                .append(" ms")
        }

        return message.toString()
    }

    @Throws(IOException::class)
    private fun createLogFromRequest(request: HttpServletRequest): String {
        val message: StringBuilder = StringBuilder()
        message.append("Request [ ")
            .append(request.method)
            .append(" ] ")
            .append(request.requestURI)
            .append("  ")

        if (StringUtils.hasText(request.queryString)) {
            message.append("[ Query Parameters ] ")
                .append(request.queryString)
                .append("  ")
        }

        val requestWrapper: ContentCachingRequestWrapper? =
            WebUtils.getNativeRequest(request, ContentCachingRequestWrapper::class.java)

        if (requestWrapper != null) {
            val payload: String = getPayloadFrom(requestWrapper)
            if (StringUtils.hasText(payload)) {
                message.append("[ Payload ] ")
                    .append(payload)
                MDC.put(TRX_PAYLOAD, payload)
            }
        }
        return message.toString()
    }

    @Throws(IOException::class)
    private fun getPayloadFrom(requestWrapper: ContentCachingRequestWrapper): String {
        if (isFormPost(requestWrapper)) return extractPayloadFromFormData(requestWrapper)
        else return extractPayloadFromInputStream(requestWrapper)
    }

    private fun isFormPost(requestWrapper: ContentCachingRequestWrapper): Boolean {
        val contentType: String? = requestWrapper.contentType
        return ((contentType != null) && contentType.contains(FORM_CONTENT_TYPE) &&
            HttpMethod.POST.matches(requestWrapper.method))
    }

    @Throws(UnsupportedEncodingException::class)
    private fun extractPayloadFromFormData(requestWrapper: ContentCachingRequestWrapper): String {
        requestWrapper.parameterMap
        val buf: ByteArray = requestWrapper.contentAsByteArray
        if (buf.isNotEmpty()) {
            val length: Int =
                min(buf.size.toDouble(), maxPayloadLength.toDouble()).toInt()
            return String(buf, 0, length, charset(requestWrapper.characterEncoding))
        }
        return ""
    }

    private fun extractPayloadFromInputStream(requestWrapper: ContentCachingRequestWrapper): String {
        val reader: BufferedReader = requestWrapper.reader
        val body: StringBuilder = StringBuilder()
        reader.lines().forEach { line: String? ->
            body.append(line).append("  ")
        }
        return if (body.isNotEmpty())
            body.toString()
        else ""

    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val requestURI: String = request.requestURI
        return excludeUris!!.stream()
            .anyMatch { uri: String? ->
                AntPathMatcher().match((uri)!!, requestURI)
            }
    }
}