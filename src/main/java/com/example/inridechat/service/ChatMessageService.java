package com.example.inridechat.service;

import com.example.inridechat.exceptions.InridechatExceptions;
import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.repository.ChatMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    private Map<String, List<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private Map<String, List<WebSocketSession>> chatRooms = new HashMap<>();
    private Map<String, List<String>> participants = new HashMap<>();
    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveChatMessage(ChatMessage message) throws InridechatExceptions {
        try {
            broadcastMessage(message, message.getTripId());
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

    public void broadcastMessage(ChatMessage message, String rideId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(message);

        if (sessions.containsKey(rideId)) {
            System.out.println("Going to broadcast to all sessions connected to rideID: " + rideId);
            List<WebSocketSession> currentSessions = sessions.get(rideId);
            System.out.println("Sending message to " + currentSessions.size() + " sessions in chat room " + rideId);
            for (WebSocketSession session : currentSessions) {
                if (session.isOpen()) {
                    System.out.println("Sending message to session: " + session.getId());
                    session.sendMessage(new TextMessage(jsonMessage));
                }
            }
        }
    }

    public void registerSession(WebSocketSession session, String rideId) {
        if (!sessions.containsKey(rideId)) {
            sessions.put(rideId, new CopyOnWriteArrayList<>());
        }
        System.out.println("Adding session to sessions!!!!!!!!!");
        sessions.get(rideId).add(session);
        System.out.println(sessions.get(rideId));
    }

    public void unregisterSession(WebSocketSession session, String rideId) {
        if (sessions.containsKey(rideId)) {
            sessions.get(rideId).remove(session);
        }
    }

    public List<ChatMessage> getPrivateMessages(Object o, String receiverId) {
        return chatMessageRepository.findByTripId(receiverId);
    }
}
