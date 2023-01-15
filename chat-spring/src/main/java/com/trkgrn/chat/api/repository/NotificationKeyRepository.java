package com.trkgrn.chat.api.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class NotificationKeyRepository {
    private final RedisTemplate template;
    private HashOperations hashOperations;
    private final String HASH_KEY = "NotificationKey";

    public NotificationKeyRepository(RedisTemplate template) {
        this.template = template;
        this.hashOperations = template.opsForHash();
    }

    public void save(String username, String notificationKey) {
//        template.opsForValue().set(username, notificationKey);
        hashOperations.put(HASH_KEY, username, notificationKey);
    }

    public Map<String, String> findAll(){
        return hashOperations.entries(HASH_KEY);
    }

    public String findNotificationKeyByUsername(String username) {
        return hashOperations.get(HASH_KEY, username).toString();
    }

    public String delete(String username) {
        hashOperations.delete(HASH_KEY, username);
        return "notification key removed";
    }
}
