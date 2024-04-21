package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void saveChatMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> getGroupMessages(String tripId) {
        return chatMessageRepository.findByTripIdAndIsGroupMessageTrue(tripId);
    }

    public List<ChatMessage> getPrivateMessages(String senderId, String receiverId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    // Method to retrieve all group messages
    public List<ChatMessage> getAllGroupMessages() {
        return chatMessageRepository.findByIsGroupMessageTrue();
    }
}
