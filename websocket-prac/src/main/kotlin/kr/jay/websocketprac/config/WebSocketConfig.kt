package kr.jay.websocketprac.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * WebSocketConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 5/15/24
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    companion object {
        private const val ENDPOINT = "/websocket-chatting"
        private const val SIMPLE_BROKER = "/topic"
        private const val PUBLISH = "/app"
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker(SIMPLE_BROKER)
        registry.setApplicationDestinationPrefixes(PUBLISH)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint(ENDPOINT)
            .setAllowedOriginPatterns("*")
    }
}