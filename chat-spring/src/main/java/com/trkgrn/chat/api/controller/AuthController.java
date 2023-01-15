package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.exception.SQLExc;
import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.service.concretes.NotificationKeyService;
import com.trkgrn.chat.api.service.concretes.TokenService;
import com.trkgrn.chat.api.service.concretes.UserService;
import com.trkgrn.chat.api.service.kafka.KafkaProducerService;
import com.trkgrn.chat.config.jwt.model.Response;
import com.trkgrn.chat.api.service.userdetail.CustomUserDetails;
import com.trkgrn.chat.api.service.userdetail.UserDetailService;
import com.trkgrn.chat.config.jwt.model.AuthRequest;
import com.trkgrn.chat.config.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {


    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UserDetailService userDetailsService;

    private final UserService userService;

    private final TokenService tokenService;

    private final NotificationKeyService notificationKeyService;

    private final KafkaProducerService kafkaProducerService;

    private final HttpServletRequest request;

    @Value("${jwt.login.expire.hours}")
    Long expireHours;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailService userDetailsService, UserService userService, TokenService tokenService, NotificationKeyService notificationKeyService, KafkaProducerService kafkaProducerService, HttpServletRequest request) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.notificationKeyService = notificationKeyService;
        this.kafkaProducerService = kafkaProducerService;
        this.request = request;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorret username or password", ex);
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails,expireHours);

        // redise kaydet
            tokenService.save(new Token(authRequest.getUsername(),jwt),expireHours);

        if (request.getHeader("NotificationToken")!=null){
            String requestKey = request.getHeader("NotificationToken");
            System.out.println(requestKey);
            notificationKeyService.save(authRequest.getUsername(),requestKey);
        }

        return new ResponseEntity<Response>(new Response(jwt, userDetails.getRole(), userDetails.getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User addedUser = null;
        try{
            addedUser = this.userService.add(user);
        }
        catch (DataIntegrityViolationException ex){
            throw new SQLExc("Sistemde bu bilgilere ait kayıt bulunmaktadır. Lütfen bilgilerinizi kontrol edin.");
        }

        kafkaProducerService.send(user.getUsername());

          return new ResponseEntity<>(addedUser,HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserRole(@PathVariable String username){
        return ResponseEntity.ok(this.userService.findByUserName(username).getRole());
    }
}
