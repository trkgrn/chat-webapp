package com.trkgrn.chat.api.model.dtos;

import com.trkgrn.chat.api.model.concretes.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ChatDto {
    private Long chatId;
    private String name;
    private UserDto origin;
    private UserDto destination;
}
