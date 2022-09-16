package com.trkgrn.chat.api.service.abstracts;

import com.trkgrn.chat.api.model.concretes.Token;

import java.util.List;

public interface ITokenService {
    public Token save(Token token);
    public Token findTokenByUsername(String username);
    public String delete(String username);
}
