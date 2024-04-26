package com.example.inridechat.websocket;

import com.example.inridechat.service.ChatMessageService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;

import java.net.URI;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatMessageService chatMessageService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String rideId = session.getAttributes().get("rideId").toString();
        // Use the rideId value here
        try {
            System.out.println("Received message: " + message.getPayload());

        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        String rideId = UriComponentsBuilder.fromUri(uri).build().getQueryParams().getFirst("rideId");
        System.out.println(rideId);
        session.getAttributes().put("rideId", rideId);
        try {
            System.out.println("New WebSocket connection established with sessionId: " + session.getId());
            chatMessageService.registerSession(session, rideId);
        } catch (Exception e) {
            System.err.println("Error during connection established: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            String rideId = session.getAttributes().get("rideId").toString();
            System.out.println("WebSocket connection closed with sessionId: " + session.getId());
            chatMessageService.unregisterSession(session, rideId);
        } catch (Exception e) {
            System.err.println("Error during connection closed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private String extractRideIdFromUri(URI uri) {
        String path = uri.getPath();
        String[] pathComponents = path.split("/");
        return pathComponents[pathComponents.length - 1];
    }
}
