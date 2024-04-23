package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;

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

    @Override
    public void addParticipant(String identifier, String participantId) {
        // This operation is not supported in private chat
        throw new UnsupportedOperationException("Add participant is not supported in private chat");
    }

    @Override
    public void removeParticipant(String identifier, String participantId) {
        // This operation is not supported in private chat
        throw new UnsupportedOperationException("Remove participant is not supported in private chat");
    }

    @Override
    public List<String> getParticipants(String identifier) {
        // This operation is not supported in private chat
        throw new UnsupportedOperationException("Get participants is not supported in private chat");
    }
}
