package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.config.jwt.model.Response;
import com.trkgrn.chat.api.service.userdetail.CustomUserDetails;
import com.trkgrn.chat.api.service.userdetail.UserDetailService;
import com.trkgrn.chat.config.jwt.model.AuthRequest;
import com.trkgrn.chat.config.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Response> creteToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorret username or password", ex);
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // rediste 10 dk kaydet


        return new ResponseEntity<Response>(new Response(jwt,userDetails.getRole(),userDetails.getId()), HttpStatus.OK);
    }
}
