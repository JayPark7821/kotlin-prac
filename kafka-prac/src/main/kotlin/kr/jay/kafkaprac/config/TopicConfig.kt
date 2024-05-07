package kr.jay.kafkaprac.config

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

/**
 * TopicConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/7/24
 */
@Configuration
class TopicConfig(
    private val admin: KafkaAdmin,
): InitializingBean {
    override fun afterPropertiesSet() {
        admin.createOrModifyTopics(
            TopicBuilder.name("test")
                .partitions(1)
                .replicas(1)
                .build()
        )
    }


}