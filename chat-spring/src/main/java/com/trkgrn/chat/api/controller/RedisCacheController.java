package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class RedisCacheController {

    private final RedisCacheService redisCacheService;

    @Autowired
    public RedisCacheController(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    @GetMapping("/test")
    public String test(){
        return redisCacheService.jwtCache();
    }

}
