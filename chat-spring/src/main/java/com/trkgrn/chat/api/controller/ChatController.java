package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.model.concretes.Message;
import com.trkgrn.chat.api.model.ws.WsMessage;
import com.trkgrn.chat.api.service.concretes.ChatService;
import com.trkgrn.chat.api.service.concretes.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;

    private final ChatService chatService;

    private final MessageService messageService;


    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.messageService = messageService;
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



    // ##########################################################################

    @MessageMapping("/chat/{to}") //to = nome canale
    public void sendMessage(@DestinationVariable String to , Message message) {
        System.out.println("handling send message: " + message + " to: " + to);
        Chat chat = this.chatService.createAndOrGetChat(to);
        message.setChat(chat);
        message.setT_stamp(this.messageService.generateTimeStamp());
        message = this.messageService.save(message);
        messagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }

    @PostMapping("/getMessages")
    public List<Message> getMessages(@RequestBody String chat) {
       Chat chat1 = this.chatService.getChatByName(chat);
        if(chat1!=null) {
            return this.messageService.getMessagesByChatName(chat);
        }
            return new ArrayList<Message>();
    }



}
