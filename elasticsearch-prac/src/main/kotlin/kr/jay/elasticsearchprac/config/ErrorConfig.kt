package kr.jay.elasticsearchprac.config

import kr.jay.elasticsearchprac.config.extension.txid
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * ErrorConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/1/24
 */

private val logger = KotlinLogging.logger {}

@Configuration
class ErrorConfig {
    @Bean
    fun errorAttribute(): DefaultErrorAttributes{
        return object: DefaultErrorAttributes(){
            override fun getErrorAttributes(
                serviceRequest: ServerRequest,
                options: ErrorAttributeOptions,
            ): MutableMap<String, Any> {
                logger.debug { "request id : ${serviceRequest.exchange().request.id}"  }
                val request = serviceRequest.exchange().request
                val txId = request.txid ?: ""//mapReqIdToTxId[request.id] ?: ""
                MDC.put(KEY_TXID, txId)
                try{
                    super.getError(serviceRequest).let{ e ->
                        logger.error(e.message ?: "Internal Server Error", e)
                    }
                    return super.getErrorAttributes(serviceRequest, options).apply {
                        remove("requestId")
                        put(KEY_TXID, txId)
                    }

                }finally {
                    request.txid = null
//                    mapReqIdToTxId.remove(request.id)
                    MDC.remove(KEY_TXID)
                }
            }
        }
    }
}