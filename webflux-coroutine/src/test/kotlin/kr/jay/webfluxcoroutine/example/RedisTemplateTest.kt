package kr.jay.webfluxcoroutine.example

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.test.context.ActiveProfiles
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration


/**
 * RedisTemplateTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/20/24
 */
@SpringBootTest
@ActiveProfiles("test")
class RedisTemplateTest(
    private val template: ReactiveRedisTemplate<Any, Any>,
): StringSpec({

    val KEY = "key"

    afterTest{
        template.delete(KEY).awaitSingle()
    }


    "hello reactive redis"{
        val ops = template.opsForValue()

        shouldThrow<NoSuchElementException> {
            ops.get(KEY).awaitSingle()
        }

        ops.set(KEY, "test value").awaitSingle()
        ops.get(KEY).awaitSingle() shouldBe "test value"

        template.expire(KEY, 3.seconds.toJavaDuration()).awaitSingle()
        delay(5.seconds)
        shouldThrow<NoSuchElementException> {
            ops.get(KEY).awaitSingle()
        }

    }
})