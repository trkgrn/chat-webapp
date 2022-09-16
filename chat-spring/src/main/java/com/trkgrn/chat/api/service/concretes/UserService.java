package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.repository.UserRepository;
import com.trkgrn.chat.api.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        return this.userRepository.findByUsername(userName);
    }
}
