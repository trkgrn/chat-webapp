package com.trkgrn.chat.api.service.abstracts;

import com.trkgrn.chat.api.model.concretes.User;

import java.util.List;

public interface IUserService{
    User findByUserName(String userName);
    User add(User user);
    List<User> getAllUser();
}
