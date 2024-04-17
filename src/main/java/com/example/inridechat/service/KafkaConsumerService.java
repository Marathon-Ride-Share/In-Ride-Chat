package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "inridechat", groupId = "group_id")
    public void listen(String jsonMessage) {
        log.info("Received message from Kafka: {}", jsonMessage);
        try {
            ChatMessage chatMessage = objectMapper.readValue(jsonMessage, ChatMessage.class);
            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage));
            log.info("Attempting to send message to WebSocket sessions: {}", textMessage.getPayload());

            if (sessions.isEmpty()) {
                log.warn("No WebSocket sessions to send the message.");
            } else {
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        session.sendMessage(textMessage);
                        log.info("Message sent to WebSocket session: {}", session.getId());
                    } else {
                        log.warn("WebSocket session is closed: {}", session.getId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error processing message from Kafka", e);
        }
    }


    public void registerSession(WebSocketSession session) {
        sessions.add(session);
        log.info("WebSocket session registered: {}", session.getId());
    }

    public void unregisterSession(WebSocketSession session) {
        sessions.remove(session);
        log.info("WebSocket session unregistered: {}", session.getId());
    }
}
