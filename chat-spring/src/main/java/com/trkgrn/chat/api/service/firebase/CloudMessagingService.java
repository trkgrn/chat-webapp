package com.trkgrn.chat.api.service.firebase;

import com.trkgrn.chat.api.model.dtos.CloudMessage;
import com.trkgrn.chat.api.service.concretes.NotificationKeyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudMessagingService {

    private final RestTemplate restTemplate;

    private final NotificationKeyService notificationKeyService;

    @Value("${firebase.cloud-messaging.url}")
    private String url;


    @Value("${firebase.cloud-messaging.server-key}")
    private String serverKey;

    public CloudMessagingService(RestTemplate restTemplate, NotificationKeyService notificationKeyService) {
        this.restTemplate = restTemplate;
        this.notificationKeyService = notificationKeyService;
    }

    public void send(String message)  {
        Map<String,String> keys = notificationKeyService.findAll();
        keys.values().stream().forEach(
                key->{
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization","key="+serverKey);

                    Map<String, Object> map = new HashMap<>();
                    map.put("to", key);
                    map.put("notification", new CloudMessage("Yeni Kullanıcı","@"+ message + " sisteme kaydoldu!"));

                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
                    ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);

                    System.out.println(response.getBody());
                }
        );
    }

}
