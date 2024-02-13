package kr.jay.webfluxcoroutine.exception

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import io.github.resilience4j.kotlin.ratelimiter.RateLimiterConfig
import io.github.resilience4j.kotlin.ratelimiter.executeSuspendFunction
import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RequestNotPermitted
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * ExternalApi
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/12/24
 */
private val logger = KotlinLogging.logger {}

@Service
class ExternalApi(
    @Value("\${api.externalUrl}")
    private val externalUrl: String
) {
    private val client = WebClient.builder().baseUrl(externalUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun delay() {
        return client.get().uri("/delay").retrieve().awaitBody()
    }

    suspend fun testCircuitBreaker(flag: String): String {
        logger.debug { "1. request call" }
        return try {
            rateLimiter.executeSuspendFunction {
                circuitbreaker.executeSuspendFunction {
                    logger.debug { "2. call external" }
                    client.get().uri("/test/circuit/child/$flag").retrieve().awaitBody()
                }
            }
        }catch (e: CallNotPermittedException){
            "Call later (blocked by circuit breaker)"
        }catch (e: RequestNotPermitted){
            "Call later (blocked by rate limiter)"
        }
    }

    val circuitbreaker = CircuitBreaker.of("test", CircuitBreakerConfig {
        slidingWindowSize(10)
        failureRateThreshold(20.0F)
        waitDurationInOpenState(10.seconds.toJavaDuration())
        permittedNumberOfCallsInHalfOpenState(3)
    })

    val rateLimiter = RateLimiter.of("rps-limiter", RateLimiterConfig {
        limitForPeriod(2)
        limitRefreshPeriod(5.seconds.toJavaDuration())
        limitRefreshPeriod(10.seconds.toJavaDuration())
    })
}