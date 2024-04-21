package com.example.inridechat.controller;

import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.service.ChatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final Map<String, ChatInterface> chatImplementations;

    @Autowired
    public ChatController(ChatInterface groupChat, ChatInterface privateChat) {
        chatImplementations = new HashMap<>();
        chatImplementations.put("group", groupChat);
        chatImplementations.put("private", privateChat);
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage message, @RequestParam(required = false) String chatType) {
        ChatInterface chat = chatImplementations.getOrDefault(chatType, chatImplementations.get("group"));
        return chat.sendMessage(message);
    }

    @GetMapping("/{type}/{identifier}")
    public List<ChatMessage> getMessages(@PathVariable String type, @PathVariable String identifier) {
        return chatImplementations.get(type).receiveMessage(identifier);
    }
}
