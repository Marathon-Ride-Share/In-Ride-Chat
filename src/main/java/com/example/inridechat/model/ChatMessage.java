package com.example.inridechat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Setter
@Getter
@Document(collection = "chatMessages")
public class ChatMessage {
    
    private Date timestamp;
    private String tripId;
    private String senderId;
    private String message;
    private String chatType;  // 'group' or 'private'

    public ChatMessage() {}

    // Getters and Setters
}