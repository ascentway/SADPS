package com.sadps.controller;

import com.sadps.entity.AuditLog;
import com.sadps.services.AuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")

public class AuditController {

    private final AuditService auditService;

    public AuditController ( AuditService auditService){
        this.auditService = auditService;
    }

    @GetMapping("/logs")
    @PreAuthorize("hasTestRole('ADMIN')")
    public List<AuditLog> getAllLogs(Authentication authentication){
        auditService.log("VIEW_AUDIT_LOGS", authentication.getName());
        return auditService.getAllLogs();
    }

}
