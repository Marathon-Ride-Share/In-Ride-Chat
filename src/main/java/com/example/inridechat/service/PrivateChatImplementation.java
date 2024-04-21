package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateChatImplementation implements ChatInterface {
    @Autowired
    private ChatMessageService chatMessageService;

    @Override
    public ChatMessage sendMessage(ChatMessage message) {
        // Logic to send message for private chat
        chatMessageService.saveChatMessage(message);
        return message;
    }

    @Override
    public List<ChatMessage> receiveMessage(String receiverId) {
        return chatMessageService.getPrivateMessages(null, receiverId);
    }
}
