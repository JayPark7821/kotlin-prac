package kr.jay.webfluxcoroutine.config

import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

/**
 * MdcFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/29/23
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcFilter: WebFilter{
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val uuid = exchange.request.headers["x-txid"]?.firstOrNull() ?:"${UUID.randomUUID()}"
        MDC.put("txid", uuid)
        return chain.filter(exchange)
    }
}