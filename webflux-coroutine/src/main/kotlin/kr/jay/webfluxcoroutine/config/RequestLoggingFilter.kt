package kr.jay.webfluxcoroutine.config

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels

/**
 * RequestLoggingFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/12/24
 */

private val logger = KotlinLogging.logger {}

@Component
@Order(11)
class RequestLoggingFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        val loggingRequest = object : ServerHttpRequestDecorator(request) {
            override fun getBody(): Flux<DataBuffer> {
                return super.getBody().doOnNext { buffer ->
                    ByteArrayOutputStream().use { output ->
                        Channels.newChannel(output).write(buffer.readableByteBuffers().next())
                        String(output.toByteArray())
                    }.let { requestBody ->
                        logger.debug { "payload: : $requestBody" }
                    }
                }
            }
        }

        return chain.filter(
            exchange.mutate()
                .request(loggingRequest)
                .build()
        )
    }
}