package com.example.websocketpracticestomp.chat.controller

import com.example.websocketpracticestomp.chat.model.ChatMessage
import com.example.websocketpracticestomp.chat.model.MessageType
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RestController

@Controller
class ChatController {

    @MessageMapping("/chat/{roomId}/message")
    @SendTo("/topic/{roomId}") // 1:n으로 메시지 송신 sendToUser는 1:1
    fun sendMessage(
        @DestinationVariable roomId: String,
        @Payload message: String,
        session: SimpMessageHeaderAccessor
    ): ChatMessage { // @Payload 어노테이션은 메시지의 페이로드를 매개변수로 받는다는 것
        val username = session.sessionAttributes?.get("username") as String
        return ChatMessage(MessageType.CHAT, roomId, message, username)
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    // SimpMessageHeaderAccessor는 WebSocket 세션의 헤더에 접근할 수 있는 클래스.
    // 이 메서드는 받은 채팅 메시지와 headerAccessor를 사용하여 사용자 이름을 세션 속성에 저장한 후, 동일한 메시지를 반환
    fun addUser(@Payload chatMessage: ChatMessage, headerAccessor: SimpMessageHeaderAccessor): ChatMessage? {
        headerAccessor.sessionAttributes!!["username"] = chatMessage.sender
        return chatMessage
    }

    /*
    @MessageMapping("/joinRoom/{roomId}")
    @SendTo("/topic/{roomId}")
    fun joinRoom(
        // @DestinationVariable roomId: String,
        @Payload chatMessage: ChatMessage,
        seesion: SimpMessageHeaderAccessor
    ): ChatMessage {
        var username = chatMessage.sender
        return ChatMessage(MessageType.JOIN, chatMessage.roomId, "$username has joined the channel.", username)
    }
    */
}