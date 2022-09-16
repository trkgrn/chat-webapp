package com.trkgrn.chat.api.service.userdetail;

import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user==null)
            throw new UsernameNotFoundException("Kullanıcı bulunamadı!");

        return new CustomUserDetails(user);
    }
}
