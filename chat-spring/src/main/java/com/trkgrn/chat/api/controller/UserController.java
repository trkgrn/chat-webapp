package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.model.dtos.UserDto;
import com.trkgrn.chat.api.service.concretes.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        System.out.println("içeride");

        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }

    @GetMapping("/searchCandidateFriends")
    public ResponseEntity<List<UserDto>>  searchCandidateFriendByUserId(@RequestParam Long userId){
        List<User> users = this.userService.searchCandidateFriendByUserId(userId);
        return ResponseEntity.ok(users
                .stream()
                .map(user -> modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/searchCandidateFriendsByUsername")
    public ResponseEntity<List<UserDto>> searchCandidateFriendByUsername(@RequestParam String token,@RequestParam String username){
        User thisUser = this.userService.findUserByToken(token);
        List<User> users = this.userService.searchCandidateFriendByUserIdAndUsername(thisUser.getUserId(),username);
        return ResponseEntity.ok(users
                .stream()
                .map(user -> modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList()));
    }



}
