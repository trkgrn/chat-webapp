package com.trkgrn.chat.api.model.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationKey implements Serializable {
    private String username;
    private String notificationKey;
}

