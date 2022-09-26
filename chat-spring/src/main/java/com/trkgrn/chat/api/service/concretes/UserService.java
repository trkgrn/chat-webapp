package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.repository.UserRepository;
import com.trkgrn.chat.config.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User findByUserName(String userName) {
        return this.userRepository.findByUsername(userName);
    }

    public User add(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User addedUser =  this.userRepository.save(user);
        return addedUser;
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User findUserByToken(String token){
        return this.findByUserName(jwtUtil.extractUsername(token));
    }

    public List<User> searchCandidateFriendByUserId(Long userId){
        return this.userRepository.searchCandidateFriendByUserId(userId);
    }

    public List<User> searchCandidateFriendByUserIdAndUsername(Long userId,String username){
        return this.userRepository.searchCandidateFriendByUserIdAndUsername(userId,username);
    }
}
