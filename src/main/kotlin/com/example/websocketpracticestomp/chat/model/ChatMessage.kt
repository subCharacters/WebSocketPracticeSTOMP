package com.example.websocketpracticestomp.chat.model

data class ChatMessage(
    var type: MessageType,
    val roomId: String,
    var content: String?,
    var sender: String
)

enum class MessageType {
    CHAT,
    JOIN,
    LEAVE
}