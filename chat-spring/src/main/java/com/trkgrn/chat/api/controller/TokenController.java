package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.service.concretes.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public Token save(@RequestBody Token token) {
        return tokenService.save(token);
    }

    @GetMapping("/{username}")
    public Token findToken(@PathVariable String username) {
        return tokenService.findTokenByUsername(username);
    }
    @DeleteMapping("/{username}")
    public String remove(@PathVariable String username)   {
        return tokenService.delete(username);
    }
}
