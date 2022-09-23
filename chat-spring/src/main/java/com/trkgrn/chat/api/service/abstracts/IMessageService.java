package com.trkgrn.chat.api.service.abstracts;

import com.trkgrn.chat.api.model.concretes.Message;

import java.util.List;

public interface IMessageService {
    public Message save(Message newMessage);
    public List<Message> getMessagesByChatName(String name);
    public String generateTimeStamp();
}
