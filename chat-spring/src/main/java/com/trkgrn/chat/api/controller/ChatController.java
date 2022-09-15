package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.WsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public void receivePublicMessage(@Payload WsMessage message){
        System.out.println(message);
    }

    @MessageMapping("/private-message")
    public void receivePrivateMessage(@Payload WsMessage message){
        System.out.println(message);
        messagingTemplate.convertAndSendToUser(message.getReceiver(),"/private",message);
        
    }



}
