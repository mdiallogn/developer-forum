package com.example.server.controller;

import com.example.server.config.websocket.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class MessageController {

    @MessageMapping("/send")
    @SendTo("/api/home")
    public Message send(Message message) {
        LocalDateTime timestamp = LocalDateTime.now();
        return new Message(message.getFrom(), message.getMessage(), timestamp);
    }
}
