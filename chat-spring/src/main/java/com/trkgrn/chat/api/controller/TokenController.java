package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.exception.NotFoundExc;
import com.trkgrn.chat.api.exception.NullPointerExc;
import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.service.concretes.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> save(@RequestBody Token token) {
        return new ResponseEntity<>(tokenService.save(token), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findToken(@PathVariable String username) {
        Token token = null;
        try{
            token = tokenService.findTokenByUsername(username);
        }
        catch (NullPointerException e){
            throw new NullPointerExc("Token bulunamadı.");
        }
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> remove(@PathVariable String username)   {
        return ResponseEntity.ok(tokenService.delete(username));
    }
}
