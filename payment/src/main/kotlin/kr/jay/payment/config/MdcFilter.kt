package kr.jay.payment.config

import io.micrometer.context.ContextRegistry
import kr.jay.payment.config.extension.txid
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.util.*

/**
 * MdcFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/29/23
 */

const val KEY_TXID = "txid"
private val logger = KotlinLogging.logger {}
//val mapReqIdToTxId = HashMap<String, String>()
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcFilter : WebFilter {

    init {
        propagateMdcThrowReactor()
    }

    private fun propagateMdcThrowReactor() {
        Hooks.enableAutomaticContextPropagation()
        ContextRegistry.getInstance().registerThreadLocalAccessor(
            KEY_TXID,
            { MDC.get(KEY_TXID) },
            { value -> MDC.put(KEY_TXID, value) },
            { MDC.remove(KEY_TXID) }
        )
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val uuid = exchange.request.headers["x-txid"]?.firstOrNull() ?: "${UUID.randomUUID()}"
        MDC.put(KEY_TXID, uuid)
        logger.debug { "request id : ${exchange.request.id}" }
        return chain.filter(exchange).contextWrite {
            Context.of(KEY_TXID, uuid)
        }.doOnError{
            exchange.request.txid = uuid
//            mapReqIdToTxId[exchange.request.id] = uuid
        }
    }
}