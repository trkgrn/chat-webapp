package com.trkgrn.chat.api.repository;

import com.sun.tools.jconsole.JConsoleContext;
import com.trkgrn.chat.api.model.concretes.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {
    private final RedisTemplate template;
    private final String HASH_KEY = "Token";

    @Autowired
    public TokenRepository(RedisTemplate template) {
        this.template = template;
    }

    public Token save(Token token){
        template.opsForValue().set(token.getUsername(),token.getJwt(),20,TimeUnit.SECONDS);
        return token;
    }

    public Token findTokenByUsername(String username){
        String jwt = template.opsForValue().get(username).toString();
        return new Token(username,jwt);
    }

    public String delete(String username){
        template.opsForValue().getAndDelete(username);
        return "token removed";
    }
}
