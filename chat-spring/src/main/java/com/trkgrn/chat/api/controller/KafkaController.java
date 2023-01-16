package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.service.kafka.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String message) {
        kafkaProducerService.send(message);
        return ResponseEntity.ok("Publish success!");
    }
}
