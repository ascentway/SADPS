package com.sadps.security.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityEvent {
    private String type;
    private String userEmail;
    private String ipAddress;
    private String details;
    private String timestamp;
}
