package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.Chat;
import com.trkgrn.chat.api.model.dtos.ChatDto;
import com.trkgrn.chat.api.repository.ChatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public ChatService(ChatRepository chatRepository, UserService userService, ModelMapper modelMapper, ImageService imageService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.imageService = imageService;
    }

    public Chat save(Chat newChat) {
        return this.chatRepository.save(newChat);
    }

    public Chat getChatByName(String name) {
        return this.chatRepository.findByName(name);
    }

    public Chat createAndOrGetChat(String name) {
        Chat chat = this.chatRepository.findByName(name);
        if (chat != null) {
            return chat;
        } else {
            Chat newChat = new Chat();
            newChat.setName(name);
            newChat.setOrigin(this.userService.findByUserName(name.split("&")[0]));
            newChat.setDestination(this.userService.findByUserName(name.split("&")[1]));
            return chatRepository.save(newChat);
        }
    }

    public List<ChatDto> findChatsByUserId(Long userId) {
        List<Chat> chats = this.chatRepository.findAllByDestination_UserIdOrOrigin_UserId(userId, userId);
        return chats.stream()
                .map(chat -> {
                    if (chat.getDestination().getImage() != null)
                        chat.getDestination().setImage(imageService.getImage(chat.getDestination().getImage().getImageId()));
                    if (chat.getOrigin().getImage() != null)
                        chat.getOrigin().setImage(imageService.getImage(chat.getOrigin().getImage().getImageId()));
                    return modelMapper.map(chat, ChatDto.class);
                })
                .collect(Collectors.toList());
    }
}
