package com.example.inridechat.service;

import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private Map<String, List<WebSocketSession>> chatRooms = new HashMap<>();

    public void saveChatMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> getGroupMessages(String tripId) {
        return chatMessageRepository.findByTripIdAndIsGroupMessageTrue(tripId);
    }

    public List<ChatMessage> getPrivateMessages(String senderId, String receiverId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    public void createChatRoom(String tripId) {
        chatRooms.putIfAbsent(tripId, new CopyOnWriteArrayList<>());
    }

    public void addUserToChat(String tripId, String userName) {
        List<WebSocketSession> sessions = chatRooms.getOrDefault(tripId, new CopyOnWriteArrayList<>());
        // This assumes user management and WebSocket session tracking is in place
    }

    public void deleteChatRoom(String tripId) {
        chatRooms.remove(tripId);
    }

    public void broadcastMessage(String payload) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(payload));
            }
        }
    }

    public void registerSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void unregisterSession(WebSocketSession session) {
        sessions.remove(session);
    }
}