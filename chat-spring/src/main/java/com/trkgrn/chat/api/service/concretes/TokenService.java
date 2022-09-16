package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.repository.TokenRepository;
import com.trkgrn.chat.api.service.abstracts.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService implements ITokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token save(Token token) {
        return this.tokenRepository.save(token);
    }


    @Override
    public Token findTokenByUsername(String username) {
        return this.tokenRepository.findTokenByUsername(username);
    }

    @Override
    public String delete(String username) {
        return this.tokenRepository.delete(username);
    }
}
