package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.service.concretes.AuthService;
import com.trkgrn.chat.config.jwt.model.AuthRequest;
import com.trkgrn.chat.config.jwt.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final HttpServletRequest request;
    private final AuthService authService;

    public AuthController(HttpServletRequest request, AuthService authService) {
        this.request = request;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) throws Exception {
        String notificationToken = null;
        if (request.getHeader("NotificationToken") != null) {
            notificationToken = request.getHeader("NotificationToken");
        }

        return new ResponseEntity<AuthResponse>(authService.login(authRequest, notificationToken), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

}
