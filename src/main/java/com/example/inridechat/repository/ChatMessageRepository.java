package com.example.inridechat.repository;

import com.example.inridechat.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByTripIdAndIsGroupMessageTrue(String tripId);
    List<ChatMessage> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
