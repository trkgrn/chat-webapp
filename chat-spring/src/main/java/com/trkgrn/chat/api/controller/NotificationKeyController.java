package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.NotificationKey;
import com.trkgrn.chat.api.service.concretes.NotificationKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("key")
public class NotificationKeyController {

    private final NotificationKeyService notificationKeyService;

    public NotificationKeyController(NotificationKeyService notificationKeyService) {
        this.notificationKeyService = notificationKeyService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody NotificationKey notificationKey) {
        notificationKeyService.save(notificationKey.getUsername(), notificationKey.getNotificationKey());
        return ResponseEntity.ok("notification key saved");
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(notificationKeyService.findAll());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        return ResponseEntity.ok(notificationKeyService.delete(username));
    }
}
