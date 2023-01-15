package com.trkgrn.chat.api.service.kafka;

import com.trkgrn.chat.api.service.firebase.CloudMessagingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    private final CloudMessagingService cloudMessagingService;

    public KafkaConsumerService(CloudMessagingService cloudMessagingService) {
        this.cloudMessagingService = cloudMessagingService;
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group_id")
    public void consume(String message)   {
        System.out.println("Consumed message: " + message);
        cloudMessagingService.send(message);
    }

}
