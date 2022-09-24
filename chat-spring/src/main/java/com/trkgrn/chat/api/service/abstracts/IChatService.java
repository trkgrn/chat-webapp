package com.trkgrn.chat.api.service.abstracts;

import com.trkgrn.chat.api.model.concretes.Chat;

import java.util.List;

public interface IChatService {
   public Chat save(Chat newChat);
   public Chat getChatByName(String name);
   public Chat createAndOrGetChat(String name);
   public List<Chat> findChatsByUserId(Long userId);
}
