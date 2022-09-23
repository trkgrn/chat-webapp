package com.trkgrn.chat.api.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String username;
    private String name;
    private String role;
    private String email;
    private String telNumber;
    private Long id;
}
