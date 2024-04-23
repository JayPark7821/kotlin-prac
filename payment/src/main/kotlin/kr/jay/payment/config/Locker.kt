package kr.jay.payment.config

import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * Locker
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/22/24
 */
private val logger = KotlinLogging.logger {}

@Component
class Locker(
    template: ReactiveRedisTemplate<Any, Any>,
) {
    private val localLock = ConcurrentHashMap<SimpleKey, Boolean>()
    private val ops = template.opsForValue()

    suspend fun <T> lock(key: SimpleKey, work: suspend () -> T): T {
        if (!tryLock(key))
            throw RuntimeException("lock failed")

        try {
            return work.invoke()
        } finally {
            unLock(key)
        }
    }

    private suspend fun tryLock(key: SimpleKey): Boolean {
        val start = System.nanoTime()
        while (
            !localLock.containsKey(key) &&
            !ops.setIfAbsent(key, true, 10.seconds.toJavaDuration()).awaitSingle()
        ) {
            delay(100)
            val elapsed = (System.nanoTime() - start).nanoseconds
             if(elapsed >= 10.seconds)
                 return false
        }
        localLock[key] = true
        return true
    }

    private suspend fun unLock(key: SimpleKey){
        try{
            ops.delete(key).awaitSingle()
        } catch (e: Exception) {
            logger.warn(e.message, e)
        }finally {
            localLock.remove(key)
        }
    }
}