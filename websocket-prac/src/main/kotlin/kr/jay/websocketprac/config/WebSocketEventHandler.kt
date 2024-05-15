package kr.jay.websocketprac.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.*

@Component
class WebSocketEventHandler {

    private val logger = KotlinLogging.logger {}

    @EventListener
    fun handleWebSocketSessionConnectEventListener(event: SessionConnectEvent) {
        logger.info { ">>> Received a SessionConnectEvent" }
    }

    @EventListener
    fun handleWebSocketSessionSubscribeEventListener(event: SessionSubscribeEvent) {
        logger.info { ">>> Received a SessionSubscribeEvent" }
    }

    @EventListener
    fun handleWebSocketSessionUnsubscribeEventListener(event: SessionUnsubscribeEvent) {
        logger.info { ">>> Received a SessionSubscribeEvent" }
    }

    @EventListener
    fun handleWebSocketSessionConnectedEventListener(event: SessionConnectedEvent) {
        logger.info { ">>> Received a SessionConnectedEvent" }
    }

    @EventListener
    fun handleWebSocketSessionDisconnectEventListener(event: SessionDisconnectEvent) {
        logger.info { ">>> Received a SessionDisconnectEvent" }
    }
}