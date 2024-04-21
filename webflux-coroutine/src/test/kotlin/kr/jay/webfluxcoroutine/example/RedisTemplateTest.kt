package kr.jay.webfluxcoroutine.example

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Range
import org.springframework.data.geo.Circle
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.data.redis.connection.DataType
import org.springframework.data.redis.connection.RedisGeoCommands
import org.springframework.data.redis.core.ReactiveListOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveZSetOperations
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.NoSuchElementException
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration


/**
 * RedisTemplateTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/20/24
 */

private val logger = KotlinLogging.logger {}

@SpringBootTest
@ActiveProfiles("test")
class RedisTemplateTest(
    private val template: ReactiveRedisTemplate<Any, Any>,
) : StringSpec({

    val KEY = "key"

    afterTest {
        template.delete(KEY).awaitSingle()
    }


    "hello reactive redis" {
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

    "LinkedList" {
        val ops = template.opsForList()
        ops.rightPushAll(KEY, 2, 3, 4, 5).awaitSingle()
        template.type(KEY).awaitSingle() shouldBe DataType.LIST
        ops.size(KEY).awaitSingle() shouldBe 4

        for (i in 0..<ops.size(KEY).awaitSingle()) {
            ops.index(KEY, i).awaitSingle().let {
                logger.debug { "$i: $it" }
            }
        }
        ops.range(KEY, 0, -1).asFlow().collect { logger.debug { it } }
        ops.range(KEY, 0, -1).toStream().forEach { logger.debug { it } }

        ops.range(KEY, 0, -1).asFlow().toList() shouldBe listOf(2, 3, 4, 5)

        ops.rightPush(KEY, 6).awaitSingle()
        ops.all(KEY) shouldBe listOf(2, 3, 4, 5, 6)

        ops.leftPop(KEY).awaitSingle() shouldBe 2
        ops.all(KEY) shouldBe listOf(3, 4, 5, 6)

        ops.leftPush(KEY, 9).awaitSingle()
        ops.rightPop(KEY).awaitSingle() shouldBe 6
        ops.all(KEY) shouldBe listOf(9, 3, 4, 5)
    }

    "LinkedList LRU" {
        val ops = template.opsForList()
        ops.rightPushAll(KEY, 7, 6, 4, 3, 2, 1, 3).awaitSingle()

        ops.remove(KEY, 0, 2).awaitSingle()
        ops.all(KEY) shouldBe listOf(7, 6, 4, 3, 1, 3)

        ops.leftPush(KEY, 0, 2).awaitSingle()
        ops.all(KEY) shouldBe listOf(2, 7, 6, 4, 3, 1, 3)
    }

    "hash" {
        val ops = template.opsForHash<Int, String>()
        val map = (1..10).map { it to "val-$it" }.toMap()
        ops.putAll(KEY, map).awaitSingle()

        ops.size(KEY).awaitSingle() shouldBe 10
        ops.get(KEY, 1).awaitSingle() shouldBe "val-1"
        ops.get(KEY, 10).awaitSingle() shouldBe "val-10"
    }

    "sorted set" {
        val ops = template.opsForZSet()
        listOf(8, 7, 1, 4, 13, 22, 9, 7, 8).forEach {
            ops.add(KEY, "$it", -0.1 * Date().time).awaitSingle()
            ops.all(KEY).let {
                logger.debug { it }
            }
        }
        template.delete(KEY).awaitSingle()

        listOf(
            "jake" to 123,
            "chulsoo" to 752,
            "yeonghee" to 932,
            "john" to 335,
            "jake" to 623,
        ).also {
            it.toMap().toList().sortedBy { it.second }.let { logger.debug { it } }
        }.forEach {
            ops.add(KEY, it.first, it.second.toDouble()).awaitSingle()
            ops.all(KEY).let { logger.debug { it } }
        }
    }

    "geo redis" {
        val ops = template.opsForGeo().also { it.delete(KEY).awaitSingle() }

        listOf(
            RedisGeoCommands.GeoLocation("seoul", Point(126.97806, 37.56667)),
            RedisGeoCommands.GeoLocation("busan", Point(129.07556, 35.17944)),
            RedisGeoCommands.GeoLocation("incheon", Point(126.70528, 37.45639)),
            RedisGeoCommands.GeoLocation("daegu", Point(128.60250, 35.87222)),
            RedisGeoCommands.GeoLocation("anyang", Point(126.95556, 37.39444)),
            RedisGeoCommands.GeoLocation("daejeon", Point(127.38500, 36.35111)),
            RedisGeoCommands.GeoLocation("gwangju", Point(126.85306, 35.15972)),
            RedisGeoCommands.GeoLocation("suwon", Point(127.02861, 37.26389)),
        ).forEach {
            ops.add(KEY, it as RedisGeoCommands.GeoLocation<Any>).awaitSingle()
        }
        ops.distance(KEY, "seoul", "busan").awaitSingle()
            .let { logger.debug { "seoul -> busan : ${it}" } }

        val position = ops.position(KEY, "seoul").awaitSingle()
        val circle = Circle(position!!, Distance(100.0, Metrics.KILOMETERS))

        ops.radius(KEY, circle).collectList().awaitSingle().map { it.content.name }.let {
            logger.debug { "close city near seoul : $it" }
            it.size shouldBe 4
        }

    }

    "hyper loglog" {
        val ops = template.opsForHyperLogLog().also { it.delete(KEY).awaitSingle() }

        val logs = (1..100_000).map { "$it" }.toTypedArray()
        ops.add(KEY, *logs).awaitSingle()

        // 원소 개수는 추정치이므로 정확하지 않다.
        ops.size(KEY).awaitSingle().let { logger.debug { it } }
    }


    "pub / sub"{
        template.listenToChannel("channel-1").doOnNext{
            logger.debug { ">> received 1 : $it" }
        }.subscribe()

        template.listenToChannel("channel-1").doOnNext{
            logger.debug { ">> received 2 : $it" }
        }.subscribe()

        template.listenToChannel("channel-1").asFlow().onEach{
            logger.debug { ">> received 3 : $it" }
        }.launchIn(CoroutineScope(Dispatchers.Default))


        repeat(10){
            val message = "test message ( ${it+1})"
            logger.debug { ">> received : $it" }
            template.convertAndSend("channel-1", message).awaitSingle()
            delay(1000)
        }
    }

})

suspend fun ReactiveListOperations<Any, Any>.all(key: Any): List<Any> {
    return this.range(key, 0, -1).asFlow().toList()
}

suspend fun ReactiveZSetOperations<Any, Any>.all(key: Any): List<Any> {
    return this.range(key, Range.closed(0, -1)).asFlow().toList()
}