package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.exception.SQLExc;
import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.service.kafka.KafkaProducerService;
import com.trkgrn.chat.api.service.userdetail.CustomUserDetails;
import com.trkgrn.chat.api.service.userdetail.UserDetailService;
import com.trkgrn.chat.config.jwt.model.AuthRequest;
import com.trkgrn.chat.config.jwt.model.AuthResponse;
import com.trkgrn.chat.config.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UserDetailService userDetailsService;

    private final UserService userService;

    private final TokenService tokenService;

    private final NotificationKeyService notificationKeyService;

    private final KafkaProducerService kafkaProducerService;

    @Value("${jwt.login.expire.hours}")
    private Long expireHours;

    public AuthService(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailService userDetailsService, UserService userService, TokenService tokenService, NotificationKeyService notificationKeyService, KafkaProducerService kafkaProducerService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.notificationKeyService = notificationKeyService;
        this.kafkaProducerService = kafkaProducerService;
    }


    public AuthResponse login(AuthRequest authRequest, String notificationToken) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorret username or password", ex);
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails, expireHours);

        // redise kaydet
        tokenService.save(new Token(authRequest.getUsername(), jwt), expireHours);

        if (notificationToken != null) {
            notificationKeyService.save(authRequest.getUsername(), notificationToken);
        }

        return new AuthResponse(jwt, userDetails.getRole(), userDetails.getId());
    }

    public String register(User user) {
        try {
            this.userService.add(user);
        } catch (DataIntegrityViolationException ex) {
            throw new SQLExc("Sistemde bu bilgilere ait kayıt bulunmaktadır. Lütfen bilgilerinizi kontrol edin.");
        }

        kafkaProducerService.send(user.getUsername());
        return "Kayıt başarılı";
    }


}
