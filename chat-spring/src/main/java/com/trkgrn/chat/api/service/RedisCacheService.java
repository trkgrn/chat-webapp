package com.trkgrn.chat.api.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

    @Cacheable(cacheNames = "jwtCache")
    public String jwtCache(){
        return "çalıştı";
    }
}
