package com.trkgrn.chat.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WsMessage {
    private String sender;
    private String receiver;
    private String message;
    private String date;
    private Status status;
}
