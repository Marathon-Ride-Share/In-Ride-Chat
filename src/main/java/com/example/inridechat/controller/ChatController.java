package com.example.inridechat.controller;

import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.service.ChatMessageService;
import com.example.inridechat.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Endpoint to send chat messages via Kafka
    @PostMapping("/send-kafka")
    public String sendKafkaMessage(@RequestParam("tripId") String tripId, @RequestParam("senderId") String senderId, @RequestParam("message") String message) {
        kafkaProducerService.sendMessage(tripId, senderId, message);
        return "Message sent for trip ID " + tripId + ": " + message;
    }

    // Endpoint to send chat messages and save to MongoDB
    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage message) {
        chatMessageService.saveChatMessage(message);
        return message;
    }

    // Retrieve all group messages for a specific trip
    @GetMapping("/group/{tripId}")
    public List<ChatMessage> getGroupMessages(@PathVariable String tripId) {
        return chatMessageService.getGroupMessages(tripId);
    }

    // Retrieve all private messages between two users
    @GetMapping("/private")
    public List<ChatMessage> getPrivateMessages(@RequestParam String senderId, @RequestParam String receiverId) {
        return chatMessageService.getPrivateMessages(senderId, receiverId);
    }
}
