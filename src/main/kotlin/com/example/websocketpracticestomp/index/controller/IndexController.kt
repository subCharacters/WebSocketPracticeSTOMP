package com.example.websocketpracticestomp.index.controller

import com.example.websocketpracticestomp.room.service.RoomService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.thymeleaf.util.StringUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
class IndexController(
    private var roomService: RoomService
) {

    @RequestMapping("/", "/index")
    fun index(): String {
        return "index"
    }

    @RequestMapping("/createUserName")
    fun createUserName(request: HttpServletRequest, session: HttpSession, model: Model): String {
        val username = request.getParameter("username")
        if (StringUtils.isEmpty(username)) {
            return "redirect:" + request.getHeader("Referer")
        }
        session.setAttribute("username", username)
        model.addAttribute("rooms", roomService.getRooms())
        return "roomList"
    }
}