package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.repository.ChatRepository;
import com.trkgrn.chat.api.service.abstracts.IChatService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Service
public class ChatService implements IChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
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
            return chatRepository.save(newChat);
        }
    }

}
