package kr.jay.webfluxcoroutine.controller

import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * RedisPubSubController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/21/24
 */
private val logger = KotlinLogging.logger {}
@RestController
class RedisPubSubController(
    private val template: ReactiveRedisTemplate<Any, Any>,
) : ApplicationListener<ApplicationReadyEvent>{



    @PostMapping("/send/{message}")
    suspend fun pub(@PathVariable message: String) {
        template.convertAndSend("test-topic", message).awaitSingle()
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        template.listenToChannel("test-topic").doOnNext{
            logger.debug { " >> received: $it" }
        }
    }
}