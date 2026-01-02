package com.sadps.security.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityEvent {
    private String eventType;
    private String userEmail;
    private Instant timestamp;
}
