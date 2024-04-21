package com.example.inridechat.controller;

import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.service.ChatInterface;
import com.example.inridechat.service.GroupChatImplementation;
import com.example.inridechat.service.PrivateChatImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private Map<String, ChatInterface> chatImplementations;

    @Autowired
    public ChatController(GroupChatImplementation groupChat, PrivateChatImplementation privateChat) {
        chatImplementations = new HashMap<>();
        chatImplementations.put("group", groupChat);
        chatImplementations.put("private", privateChat);
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage message, @RequestParam(required = false) String chatType) {
        ChatInterface chat = chatImplementations.getOrDefault(chatType, chatImplementations.get("group"));
        chat.sendMessage(message);
        return message;
    }

    @GetMapping("/{type}/{identifier}")
    public List<ChatMessage> getMessages(@PathVariable String type, @PathVariable String identifier) {
        ChatInterface chat = chatImplementations.get(type);
        return chat.receiveMessage(identifier);
    }
}
