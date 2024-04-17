package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "inridechat";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String tripId, String senderId, String message) {
        ChatMessage chatMessage = new ChatMessage(tripId, senderId, message, System.currentTimeMillis());
        try {
            String jsonMessage = objectMapper.writeValueAsString(chatMessage);
            kafkaTemplate.send(TOPIC, jsonMessage);
            log.info("Message sent to Kafka topic {}: {}", TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error while sending message to Kafka", e);
        }
    }
}
