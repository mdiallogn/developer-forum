package com.example.server.controller;

import com.example.server.config.websocket.Message;
import com.example.server.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/test")
    @SendTo("/api/hi")
    public Message greeting(User user) {
        return new Message("Hi, " + user.getLastName() + "!");
    }
}
