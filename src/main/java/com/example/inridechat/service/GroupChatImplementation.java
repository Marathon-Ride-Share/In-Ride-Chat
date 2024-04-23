package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Service
@Primary
public class GroupChatImplementation implements ChatInterface {
    @Autowired
    private ChatMessageService chatMessageService;

    @Override
    public ChatMessage sendMessage(ChatMessage message) {
        chatMessageService.saveChatMessage(message);
        return message;
    }

    @Override
    public List<ChatMessage> receiveMessage(String tripId) {
        return chatMessageService.getGroupMessages(tripId);
    }

    @Override
    public void addParticipant(String tripId, String participantId) {
        chatMessageService.addUserToChat(tripId, participantId);
    }

    @Override
    public void removeParticipant(String tripId, String participantId) {
        chatMessageService.removeUserFromChat(tripId, participantId);
    }

    @Override
    public List<String> getParticipants(String tripId) {
        return chatMessageService.getChatParticipants(tripId);
    }
}
