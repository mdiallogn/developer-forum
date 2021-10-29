package com.example.server.controller;

import com.example.server.config.websocket.Greeting;
import com.example.server.config.websocket.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping("/api")
public class SocketController {

    @GetMapping("/hello")
    @MessageMapping("/hello")
    @SendTo("/api/hello")
    public Greeting greeting(Message message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getMessage()) + "!");
    }

    @GetMapping()
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("Home page", HttpStatus.OK);
    }

}
