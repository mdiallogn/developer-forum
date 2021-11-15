package com.example.server.controller;

import com.example.server.config.websocket.Message;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/test")
    @SendTo("/api/hi")
    public Message greeting(UserEntity user) {
        Logger logger = LoggerFactory.getLogger(SocketController.class);
        logger.info("Message reçu ! " + user.getLastName());
        return new Message("Hi, " + user.getLastName() + "!");
    }
}
