package kr.jay.payment.service.api

import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import kr.jay.payment.controller.RequestPaySucceed
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

/**
 * TossPayApi
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/1/24
 */
@Service
class TossPayApi(
    @Value("\${payment.toss.domain}")
    private val domain: String,
    @Value("\${payment.toss.key.secret}")
    private val secret: String,
) {

    private val client = createClient()
    private fun createClient(): WebClient {
        val insecureSslContext =
            SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()
        val provider = ConnectionProvider.builder("toss-pay")
            .maxConnections(10)
            .pendingAcquireTimeout(Duration.ofSeconds(10))
            .build()
        val connector =
            ReactorClientHttpConnector(HttpClient.create(provider).secure { it.sslContext(insecureSslContext) })

        return WebClient.builder()
            .baseUrl(domain)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(connector)
            .build()
    }

    suspend fun confirm(request: RequestPaySucceed): ResConfirm {
        return client.post()
            .uri("/v1/payments/confirm")
            .header("Authorization", "Basic $secret")
            .bodyValue(request)
            .retrieve()
            .awaitBody()
    }
}

data class ResConfirm(
    val paymentKey: String,
    val orderId: String,
    val status: String,
    val totalAmount: Long,
    val method: String,
)