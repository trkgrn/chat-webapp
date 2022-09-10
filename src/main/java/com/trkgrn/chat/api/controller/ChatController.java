package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.WsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*")
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    //@SendTo("/topic")
    //@SendToUser()
    public void chatEndpoint(@Payload WsMessage wsMessage) {
        System.out.println("Mesaj: "+wsMessage);
        messagingTemplate.convertAndSend("/topic", wsMessage);
    }
}
