package com.example.inridechat.service;

import com.example.inridechat.exceptions.InridechatExceptions;
import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private Map<String, List<WebSocketSession>> chatRooms = new HashMap<>();
    private Map<String, List<String>> participants = new HashMap<>();
    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveChatMessage(String rideId, ChatMessage message) throws InridechatExceptions {
        message.setTripId(rideId);
        try {
            return chatMessageRepository.save(message);
        } catch (Exception e) {
            throw new InridechatExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to save chat message");
        }
    }

    public List<ChatMessage> getChatMessage(String rideId) throws InridechatExceptions {
        try {
            return chatMessageRepository.findByTripId(rideId);
        } catch (Exception e) {
            throw new InridechatExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve chat messages");
        }
    }

    public List<ChatMessage> getGroupMessages(String tripId) {
        return chatMessageRepository.findByTripId(tripId);
    }



    public void createChatRoom(String tripId) {
        chatRooms.putIfAbsent(tripId, new CopyOnWriteArrayList<>());
        participants.putIfAbsent(tripId, new ArrayList<>());
    }

    public void addUserToChat(String tripId, String userName) {
        participants.getOrDefault(tripId, new ArrayList<>()).add(userName);
    }

    public void removeUserFromChat(String tripId, String userName) {
        participants.getOrDefault(tripId, new ArrayList<>()).remove(userName);
    }

    public List<String> getChatParticipants(String tripId) {
        return participants.getOrDefault(tripId, new ArrayList<>());
    }

    public void deleteChatRoom(String tripId) {
        chatRooms.remove(tripId);
        participants.remove(tripId);
    }

    public void broadcastMessage(String payload) throws IOException {
        System.out.println("Broadcasting message to " + sessions.size() + " sessions.");
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                System.out.println("Sending message to session: " + session.getId());
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
