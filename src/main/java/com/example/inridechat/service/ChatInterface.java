package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import java.util.List;

public interface ChatInterface {
    ChatMessage sendMessage(ChatMessage message);
    List<ChatMessage> receiveMessage(String identifier);

    void addParticipant(String tripId, String participantId);

    void removeParticipant(String tripId, String participantId);

    List<String> getParticipants(String tripId);
}
