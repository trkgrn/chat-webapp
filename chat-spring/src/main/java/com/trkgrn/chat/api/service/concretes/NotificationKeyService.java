package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.repository.NotificationKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationKeyService {

    private final NotificationKeyRepository notificationKeyRepository;

    public NotificationKeyService(NotificationKeyRepository notificationKeyRepository) {
        this.notificationKeyRepository = notificationKeyRepository;
    }

    public void save(String username, String notificationKey) {
        notificationKeyRepository.save(username, notificationKey);
    }

    public Map<String,String> findAll() {
        return notificationKeyRepository.findAll();
    }

    public String findNotificationKeyByUsername(String username) {
        return notificationKeyRepository.findNotificationKeyByUsername(username);
    }

    public String delete(String username) {
        return notificationKeyRepository.delete(username);
    }


}
