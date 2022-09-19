package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.exception.ExpiredJwtExc;
import com.trkgrn.chat.api.exception.NotFoundExc;
import com.trkgrn.chat.api.exception.NullPointerExc;
import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.model.concretes.User;
import com.trkgrn.chat.api.service.concretes.TokenService;
import com.trkgrn.chat.api.service.userdetail.CustomUserDetails;
import com.trkgrn.chat.api.service.userdetail.UserDetailService;
import com.trkgrn.chat.config.jwt.service.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
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
    private JwtUtil jwtUtil;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    private UserDetailService userDetailsService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Token token) {
        return new ResponseEntity<>(tokenService.save(token), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findToken(@PathVariable String username) {
        Token token = null;
        try {
            token = tokenService.findTokenByUsername(username);
            //  CustomUserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token.getJwt()));
            //  System.out.println("user: "+userDetails.getUser());
        } catch (NullPointerException e) {
            throw new NullPointerExc("Oturum süresi sona erdi.");
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping("/values/{token}")
    public ResponseEntity<?> findUserByToken(@PathVariable String token) {
        User user = null;
        try {
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
            user = userDetails.getUser();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtExc("Oturum süresi sona erdi.");
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> remove(@PathVariable String username) {
        return ResponseEntity.ok(tokenService.delete(username));
    }

    @GetMapping("/isTokenExpired/{token}")
    public ResponseEntity<?> isTokenExpired(@PathVariable String token) {
        String username;
        Token tokenObj;
        try {
            username = this.jwtUtil.extractUsername(token);
            tokenObj = this.tokenService.findTokenByUsername(username);
        } catch (NullPointerException e) {
            throw new NullPointerExc("Oturum süresi sona erdi.");
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtExc("Oturun süresi sona erdi.");
        }
        if(token.equals(tokenObj.getJwt())){
            return ResponseEntity.ok("Oturum devam ediyor.");
        }
        return ResponseEntity.ok("Oturum sona erdi");
    }
}
