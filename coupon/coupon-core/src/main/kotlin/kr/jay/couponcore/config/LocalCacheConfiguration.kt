package kr.jay.couponcore.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

/**
 * LocalCacheConfiguration
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/9/24
 */
@Configuration
class LocalCacheConfiguration {

    @Bean
    fun localCacheManager() =
        CaffeineCacheManager().apply {
            setCaffeine(
                Caffeine.newBuilder()
                    .expireAfterWrite(Duration.ofSeconds(10))
                    .maximumSize(1000)
            )
        }
}