package com.sadps.security.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityEventProducer {
    private final KafkaTemplate<String, SecurityEvent> kafkaTemplate;
    private static final String TOPIC = "security-events";

    public void publish(SecurityEvent event){
        kafkaTemplate.send(TOPIC, event);
    }
}
