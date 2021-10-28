package com.example.server.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;


@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();
        System.out.println("Server received: "+ request);

        String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));
        System.out.println("Server sends: "+response);
        session.sendMessage(new TextMessage(response));
    }

}
