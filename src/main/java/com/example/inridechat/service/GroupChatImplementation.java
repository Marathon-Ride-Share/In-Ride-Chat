package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GroupChatImplementation implements ChatInterface {
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public void sendMessage(ChatMessage message) {
        kafkaProducerService.sendMessage(message.getTripId(), message.getSenderId(), message.getMessage());
        chatMessageService.saveChatMessage(message);
    }

    @Override
    public List<ChatMessage> receiveMessage(String tripId) {
        return chatMessageService.getGroupMessages(tripId);
    }
}
