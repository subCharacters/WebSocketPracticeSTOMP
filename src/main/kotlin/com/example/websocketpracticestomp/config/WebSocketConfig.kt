package com.example.websocketpracticestomp.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker // Websocket을 통한 메시징 기능을 활성화
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()
        /*
        * withSockJS()
        * 는 웹소켓을 지원하지 않는 브라우저에
        * 폴백 옵션을 활성화하는데 사용됩니다.
        * */
    }

    // 메시지 주고 받을 엔드포인트에 대한 Prefix
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/chat") // 서버가 목적지 일때(Client -> Server 메시지 전송시 Endpoint)
        registry.enableSimpleBroker("/topic") // 클라이언트가 Subscribe 할떄(Server -> Client 메시지 전송 시 Endpoint)
    }
}