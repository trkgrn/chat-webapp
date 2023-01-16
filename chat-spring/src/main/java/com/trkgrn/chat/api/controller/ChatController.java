package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.model.concretes.Message;
import com.trkgrn.chat.api.model.dtos.ChatDto;
import com.trkgrn.chat.api.model.ws.WsMessage;
import com.trkgrn.chat.api.service.concretes.ChatService;
import com.trkgrn.chat.api.service.concretes.ImageService;
import com.trkgrn.chat.api.service.concretes.MessageService;
import com.trkgrn.chat.api.service.concretes.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;

    private final ChatService chatService;

    private final MessageService messageService;

    private final UserService userService;

    private final ImageService imageService;

    private final ModelMapper modelMapper;


    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService, MessageService messageService, UserService userService, ImageService imageService, ModelMapper modelMapper) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.messageService = messageService;
        this.userService = userService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @MessageMapping("/chat")
    //@SendTo("/topic")
    //@SendToUser()
    public void chatEndpoint(@Payload WsMessage wsMessage) {
        System.out.println("Mesaj: " + wsMessage);
        messagingTemplate.convertAndSend("/topic", wsMessage);
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public void receivePublicMessage(@Payload WsMessage message) {
        System.out.println(message);
    }

    @MessageMapping("/private-message")
    public void receivePrivateMessage(@Payload WsMessage message) {
        System.out.println(message);
        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message);

    }


    // ##########################################################################

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("handling send message: " + message + " to: " + to);
        Chat chat = this.chatService.createAndOrGetChat(to);
        message.setChat(chat);
        message.setT_stamp(this.messageService.generateTimeStamp());
        message = this.messageService.save(message);
        messagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }

    @PostMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages(@RequestBody String chatName) {
        Chat chat = this.chatService.getChatByName(chatName);
        if (chat != null) {
            return ResponseEntity.ok(this.messageService.getMessagesByChatName(chatName));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/getChats/{token}")
    public ResponseEntity<List<ChatDto>> getAllChatByUserId(@PathVariable String token) {
        return ResponseEntity.ok(chatService.findChatsByUserId(this.userService.findUserByToken(token).getUserId()));
    }
}
