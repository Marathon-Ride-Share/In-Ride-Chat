package com.example.inridechat.websocket;

import com.example.inridechat.service.ChatMessageService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatMessageService chatMessageService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            System.out.println("Received message: " + message.getPayload());
            chatMessageService.broadcastMessage(message.getPayload());
        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            System.out.println("New WebSocket connection established with sessionId: " + session.getId());
            chatMessageService.registerSession(session);
        } catch (Exception e) {
            System.err.println("Error during connection established: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            System.out.println("WebSocket connection closed with sessionId: " + session.getId());
            chatMessageService.unregisterSession(session);
        } catch (Exception e) {
            System.err.println("Error during connection closed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
