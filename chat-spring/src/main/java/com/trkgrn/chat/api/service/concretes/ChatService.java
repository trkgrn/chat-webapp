package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.repository.ChatRepository;
import com.trkgrn.chat.api.repository.UserRepository;
import com.trkgrn.chat.api.service.abstracts.IChatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements IChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Chat save(Chat newChat) {
        return this.chatRepository.save(newChat);
    }

    @Override
    public Chat getChatByName(String name) {
        return this.chatRepository.findByName(name);
    }

    @Override
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

    @Override
    public List<Chat> findChatsByUserId(Long userId) {
        return this.chatRepository.findAllByDestination_UserIdOrOrigin_UserId(userId,userId);
    }


}
