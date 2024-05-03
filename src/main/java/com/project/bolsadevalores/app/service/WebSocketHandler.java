package com.project.bolsadevalores.app.service;

import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Nova conexão estabelecida: " + session.getId());
    }
    


    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                System.out.println("Enviando mensagem para: " + session.getId());
                session.sendMessage(new TextMessage(message));
            }
        }
    }
    
}
