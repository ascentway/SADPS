package com.sadps.services;

import com.sadps.entity.AuditLog;
import com.sadps.respository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AuditService {
    private final AuditRepository auditRepository;

    public void log(String action, String email){
        AuditLog log = AuditLog.builder()
                .action(action)
                .performedBy(email)
                .timestamp(Instant.now())
                .build();

        auditRepository.save(log);
    }

    public List<AuditLog> getAllLogs(){
        return auditRepository.findAll();
    }
}
