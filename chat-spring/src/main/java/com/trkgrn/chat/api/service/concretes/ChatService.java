package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.repository.ChatRepository;
import com.trkgrn.chat.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Chat save(Chat newChat) {
        return this.chatRepository.save(newChat);
    }

    public Chat getChatByName(String name) {
        return this.chatRepository.findByName(name);
    }

    public Chat createAndOrGetChat(String name) {
        Chat ce = this.chatRepository.findByName(name);
        if (ce != null) {
            return ce;
        }
        else {
            Chat newChat = new Chat();
            newChat.setName(name);
            newChat.setOrigin(this.userRepository.findByUsername(name.split("&")[0]));
            newChat.setDestination(this.userRepository.findByUsername(name.split("&")[1]));
            return chatRepository.save(newChat);
        }
    }

    public List<Chat> findChatsByUserId(Long userId) {
        return this.chatRepository.findAllByDestination_UserIdOrOrigin_UserId(userId,userId);
    }


}
