package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.service.concretes.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        System.out.println("içeride");
        return ResponseEntity.ok("Deneme");
    }



}
