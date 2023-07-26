package com.example.websocketpracticestomp.chat.listener

import com.example.websocketpracticestomp.chat.model.ChatMessage
import com.example.websocketpracticestomp.chat.model.MessageType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener(
    private val messagingTemplate: SimpMessageSendingOperations?
) {
    private val logger: Logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)

    @EventListener // 해당 메서드가 이벤트를 처리하기 위한 리스너 역할
    fun handleWebSocketConnectListener(event: SessionConnectedEvent?) {
        // SessionConnectedEvent는 WebSocket 클라이언트가 서버에 연결될 때 발생하는 이벤트
        logger.info("Received a new web socket connection")
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        // SessionDisconnectEvent는 WebSocket 클라이언트가 서버에서 연결을 해제할 때 발생하는 이벤트
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        val username = headerAccessor.sessionAttributes!!["username"] as String?
        val roomId = headerAccessor.sessionAttributes!!["roomId"] as String?
        if (username != null) {
            logger.info("User Disconnected : $username")
            val chatMessage = ChatMessage(MessageType.LEAVE, "", "", username)

            // messagingTemplate을 사용하여 "/topic/public" 주소로 메시지를 변환하여 전송.
            // 연결 해제 메시지가 해당 주소를 구독하는 클라이언트에게 전송
            messagingTemplate!!.convertAndSend("/topic/$roomId", chatMessage)
        }
    }
}