package com.trkgrn.chat.api.repository;

import com.trkgrn.chat.api.model.concretes.Token;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {
    private final RedisTemplate template;

    public TokenRepository(RedisTemplate template) {
        this.template = template;
    }

    public Token save(Token token, Long expiredTime) {
        template.opsForValue().set(token.getUsername(), token.getJwt(), expiredTime, TimeUnit.HOURS);
        return token;
    }

    public Token findTokenByUsername(String username) {
        String jwt = template.opsForValue().get(username).toString();
        return new Token(username, jwt);
    }

    public String delete(String username) {
        template.opsForValue().getAndDelete(username);
        return "token removed";
    }
}
