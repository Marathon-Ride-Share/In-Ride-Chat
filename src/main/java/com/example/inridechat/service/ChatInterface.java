package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import java.util.List;

public interface ChatInterface {
    void sendMessage(ChatMessage message);
    List<ChatMessage> receiveMessage(String identifier);
}
