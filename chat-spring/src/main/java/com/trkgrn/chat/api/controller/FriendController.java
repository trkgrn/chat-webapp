package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.model.concretes.Friend;
import com.trkgrn.chat.api.service.concretes.ChatService;
import com.trkgrn.chat.api.service.concretes.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {
    private final FriendService friendService;

    private final ChatService chatService;

    public FriendController(FriendService friendService, ChatService chatService) {
        this.friendService = friendService;
        this.chatService = chatService;
    }

    @PostMapping("/addFriend")
    public ResponseEntity<Friend> addFriend(@RequestBody Friend friend){
        Friend addedFriendship = this.friendService.addFriend(friend);
        String chatName = addedFriendship.getFriended().getUsername()+"&"+addedFriendship.getFriender().getUsername();
        Chat newChat = this.chatService.createAndOrGetChat(chatName);
        return ResponseEntity.ok(friend);
    }
}
