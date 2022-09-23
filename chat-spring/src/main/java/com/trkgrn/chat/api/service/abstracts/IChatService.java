package com.trkgrn.chat.api.service.abstracts;

import com.trkgrn.chat.api.model.concretes.Chat;

public interface IChatService {
   public Chat save(Chat newChat);
   public Chat getChatByName(String name);
   public Chat createAndOrGetChat(String name);
}
