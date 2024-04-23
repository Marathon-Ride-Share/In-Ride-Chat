package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;

import java.util.List;

public abstract class ExtendedChatInterface implements ChatInterface {
    public abstract void addParticipant(String identifier, String participantId);
    public abstract void removeParticipant(String identifier, String participantId);
    public abstract List<String> getParticipants(String identifier);
}
