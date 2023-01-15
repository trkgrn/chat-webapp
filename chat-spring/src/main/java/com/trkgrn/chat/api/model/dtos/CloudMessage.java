package com.trkgrn.chat.api.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class CloudMessage {
    private String title;
    private String body;
}
