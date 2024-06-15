package kr.jay.paymentservice.payment.adapter.out.web.config

import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * TossWebClientConfiguration
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/29/24
 */
@Configuration
class TossWebClientConfiguration(
    @Value("\${psp.toss.url}") private val baseUrl: String,
    @Value("\${psp.toss.secretKey}") private val secretKey: String,
) {
    @Bean
    fun tossPaymentClient(): WebClient {
        val encodedSecretKey = Base64.getEncoder().encodeToString(("$secretKey:").toByteArray())

        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic $encodedSecretKey")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(reactorClientHttpConnector())
            .codecs { it.defaultCodecs() }
            .build()
    }

    private fun reactorClientHttpConnector(): ClientHttpConnector {
        val provider = ConnectionProvider.builder("toss-payment").build()

        val clientBase = HttpClient.create(provider)
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(30, TimeUnit.SECONDS))
            }
        
        return ReactorClientHttpConnector(clientBase)
    }

}