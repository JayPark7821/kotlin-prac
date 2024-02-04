package kr.jay.couponcore.component

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * DistributeLockExecutor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */
@Component
class DistributeLockExecutor(
    private val redissonClient: RedissonClient
) {

    fun execute(
        lockName: String,
        waitMillisecond: Long,
        leaseMilliSecond: Long,
        logic: Runnable
    ) {
        val lock = redissonClient.getLock(lockName)

        try {
            val isLocked = lock.tryLock(waitMillisecond, leaseMilliSecond, TimeUnit.MILLISECONDS)
            check(isLocked) { "[ $lockName ] lock 획득 실패" }
            logic.run()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            if(lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }
}