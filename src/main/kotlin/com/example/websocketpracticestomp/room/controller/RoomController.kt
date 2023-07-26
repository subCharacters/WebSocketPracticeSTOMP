package com.example.websocketpracticestomp.room.controller

import com.example.websocketpracticestomp.chat.model.ChatMessage
import com.example.websocketpracticestomp.chat.model.MessageType
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
class RoomController {

    @RequestMapping("/chat/{roomId}")
    fun pageRedircet(model: Model, session: HttpSession): String {
        var username = session.getAttribute("username")
        model.addAttribute("username", username)
        return "chatRoom"
    }

    @MessageMapping("/joinRoom/{roomId}")
    @SendTo("/topic/{roomId}")
    fun joinRoom(
        // @DestinationVariable roomId: String,
        @Payload chatMessage: ChatMessage,
        seesion: SimpMessageHeaderAccessor
    ): ChatMessage {
        var username = chatMessage.sender
        var roomId = chatMessage.roomId
        seesion.sessionAttributes!!["username"] = username
        seesion.sessionAttributes!!["username"] = roomId
        return ChatMessage(MessageType.JOIN, chatMessage.roomId, "$username has joined the channel.", username)
    }
}