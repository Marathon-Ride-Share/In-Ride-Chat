package com.example.inridechat.controller;

import com.example.inridechat.exceptions.InridechatExceptions;
import com.example.inridechat.model.ChatMessage;
import com.example.inridechat.service.ChatInterface;
import com.example.inridechat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }
    // we should first define a model, the model is made up for the follow strcuture:{
    //key: groupId
    // value: senderId, timestamp, message, chatType

//}


//    @PostMapping("/send/{rideId}")
//    public ChatMessage sendMessage(@RequestBody ChatMessage message, @PathVariable String rideId) {
//        message.setTripId(rideId);
//        // from request body get the userid, message, timestamp, from request param get the rideid, and save it into the mongodb
//        // like this format: {"message":{"message":"fshs","timestamp":1714083849132,"sender":"simon2","chatType":"group"}}
//
//        // return status code
//    }

    @PostMapping("/send/{rideId}")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage message, @PathVariable String rideId) {
        try {
            ChatMessage response = chatMessageService.saveChatMessage(rideId,message);
            return ResponseEntity.ok(response);
        } catch(InridechatExceptions e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<?> getMessages(@PathVariable String rideId) {
        // from request param get the rideId, retrieve all the information from the db according to the rideId
        try {
            List<ChatMessage> response = chatMessageService.getChatMessage(rideId);
            return ResponseEntity.ok(response);
        } catch(InridechatExceptions e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

}

