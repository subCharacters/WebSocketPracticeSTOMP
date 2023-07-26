package com.example.websocketpracticestomp.room.service

import com.example.websocketpracticestomp.room.model.Room
import org.springframework.stereotype.Service

@Service
class RoomService {

    private val rooms: List<Room> = mutableListOf<Room>().apply {
        for (i in 1..5) {
            val roomId = "room$i"
            val roomName = "room name $i"
            add(Room(roomId, roomName))
        }
    }

    fun getRooms(): List<Room> {
        return rooms
    }

    fun getRoomById(roomId: String): Room? {
        return rooms.find { it.id == roomId }
    }
}