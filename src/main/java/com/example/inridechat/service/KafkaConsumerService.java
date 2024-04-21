package com.example.inridechat.service;

import com.example.inridechat.model.KafkaChatGroupEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ChatMessageService chatMessageService;  // Service to manage chat messages and rooms

    @KafkaListener(topics = "user-chat", groupId = "inridechat")
    public void listen(String message) {
        try {
            KafkaChatGroupEvent event = objectMapper.readValue(message, KafkaChatGroupEvent.class);
            processEvent(event);
        } catch (Exception e) {
            log.error("Error processing Kafka event", e);
        }
    }

    private void processEvent(KafkaChatGroupEvent event) {
        switch (event.getAction()) {
            case CREATE:
                chatMessageService.createChatRoom(event.getRideId());
                break;
            case ADD_USER:
                chatMessageService.addUserToChat(event.getRideId(), event.getUserName());
                break;
            case DELETE:
                chatMessageService.deleteChatRoom(event.getRideId());
                break;
        }
    }
}
