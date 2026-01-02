package com.sadps.services;

import com.sadps.entity.AuditLog;
import com.sadps.respository.AuditRepository;
import com.sadps.security.kafka.SecurityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor

public class AuditService {

    private final AuditRepository auditRepository;

    public void save(SecurityEvent event){
        AuditLog log = AuditLog.builder()
                .action(event.getEventType())
                .performedBy(event.getUserEmail())
                .timestamp(Instant.now())
                .source("KAFKA")
                .build();

        auditRepository.save(log);
    }

}
