package com.sadps.security.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SecurityEventProducer {

    private static final String TOPIC = "security-events";

    private final KafkaTemplate<String, SecurityEvent> kafkaTemplate;

    public void publish(SecurityEvent event) {
        event.setTimestamp(Instant.now());
        kafkaTemplate.send(TOPIC, event.getUserEmail(), event);
    }
}
