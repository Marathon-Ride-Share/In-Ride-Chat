package com.example.inridechat.websocket;

import com.example.inridechat.service.KafkaProducerService;
import com.example.inridechat.service.KafkaConsumerService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private KafkaProducerService kafkaProducerService;
    private KafkaConsumerService kafkaConsumerService;

    @Autowired
    public ChatWebSocketHandler(KafkaProducerService kafkaProducerService, KafkaConsumerService kafkaConsumerService) {
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Assuming the message payload is directly the message text
        kafkaProducerService.sendMessage("12345", "user123", message.getPayload());
        log.info("WebSocket message received and sent to Kafka: {}", message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        kafkaConsumerService.registerSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        kafkaConsumerService.unregisterSession(session);
    }
}
