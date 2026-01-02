package com.sadps.security.kafka;

import com.sadps.entity.AuditLog;
import com.sadps.respository.AuditRepository;
import com.sadps.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityEventConsumer {

//    private final AuditRepository auditRepository;
    private final AuditService auditService;
    @KafkaListener(
            topics = "security-events",
            groupId = "security-audit-group"
    )

    public void consume(SecurityEvent event){
        log.info("Security Event Received: {}", event);

//        AuditLog logEntry = AuditLog.builder()
//                .action(event.getEventType())
//                .performedBy(event.getUserEmail())
//                .timestamp(event.getTimestamp())
//                .build();

//        auditRepository.save (logEntry);
        auditService.save(event);
    }

}
