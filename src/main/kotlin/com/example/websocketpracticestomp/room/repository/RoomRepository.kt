package com.example.websocketpracticestomp.room.repository

import com.example.websocketpracticestomp.room.model.Room
import java.util.HashMap

class RoomRepository {
    var roomMap = HashMap<String?, Room>()
    companion object {
        var rooms: Collection<Room>? = null
    }

    constructor() {
        for (i in 0..1) {
            val uuid = "room" + i
            /*val chatRoom = Room(uuid)
            roomMap[chatRoom.id] = chatRoom
            println("chatRoom 클래스를 복제하고 있습니다.")
            println("chatRoom -> $chatRoom")*/
        }
        rooms = roomMap.values
    }

    fun getRoom(id: String?): Room? {
        return roomMap[id]
    }
}