package kr.jay.payment.config

import mu.KotlinLogging
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

/**
 * WithRedisContainer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/5/24
 */
private val logger = KotlinLogging.logger {}
interface WithRedisContainer{
    companion object{
        private val container = GenericContainer(DockerImageName.parse("redis")).apply {
            addExposedPort(6379)
            start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperty(registry: DynamicPropertyRegistry){
            registry.add("spring.data.redis.port"){
                "${container.getMappedPort(6379)}"
            }
        }
    }
}