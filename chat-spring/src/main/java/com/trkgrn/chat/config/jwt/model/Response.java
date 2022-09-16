package com.trkgrn.chat.config.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private String token;
    private String role;
    private Long id;
}
