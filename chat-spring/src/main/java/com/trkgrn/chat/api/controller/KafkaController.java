package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.service.kafka.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
