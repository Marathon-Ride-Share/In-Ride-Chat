package com.example.inridechat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatMessage {
    @Id
    private String id;
    private String tripId;
    private String senderId;
    private String receiverId; // For private chat, this will be the ID of the receiver user
    private String message;
    private long timestamp;
    private boolean isGroupMessage; // Flag to indicate if it's a group message

    // No-argument constructor
    public ChatMessage() {
    }

    // All-arguments constructor
    public ChatMessage(String tripId, String senderId, String message, long timestamp) {
        this.tripId = tripId;
        this.senderId = senderId;
        this.receiverId = null; // No specific receiver
        this.message = message;
        this.timestamp = timestamp;
        this.isGroupMessage = true; // Assume it's a group message if no receiver is specified
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isGroupMessage() {
        return isGroupMessage;
    }

    public void setGroupMessage(boolean groupMessage) {
        isGroupMessage = groupMessage;
    }
}
