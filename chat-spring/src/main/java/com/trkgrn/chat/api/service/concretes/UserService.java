package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.repository.UserRepository;
import com.trkgrn.chat.api.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public User add(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User addedUser =  this.userRepository.save(user);
        return addedUser;
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }
}
