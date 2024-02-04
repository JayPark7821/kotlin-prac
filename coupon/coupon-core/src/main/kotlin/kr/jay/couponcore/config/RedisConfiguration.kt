package kr.jay.couponcore.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * RedisConfiguration
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/2/24
 */
@Configuration
class RedisConfiguration(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int
) {

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config().apply {
            useSingleServer().address = "redis://$host:$port"
        }
        return Redisson.create(config)
    }
}