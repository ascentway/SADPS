package com.sadps.controller;


import com.sadps.respository.AuditRepository;
import com.sadps.security.redis.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class SecurityDebugController {

    private final AuditRepository auditRepository;
    private final LoginAttemptService loginAttemptService;

    @GetMapping("/audits/count")
    public long auditCount(){
        return auditRepository.count();
    }

    @GetMapping("/redis/attempts/{email}")
    public int redisAttempts(@PathVariable String email){
        return loginAttemptService.getAttempts(email);
    }

    @DeleteMapping("/redis/clear/{email}")
    public void clearRedis(@PathVariable String email){
        loginAttemptService.reset(email);
    }

}
