package com.sadps.services;

import com.sadps.dto.SignupRequest;
import com.sadps.entity.User;
import com.sadps.exceptions.UnauthorizedException;
import com.sadps.respository.UserRepository;
import com.sadps.security.Role;
import com.sadps.security.jwt.JwtService;
import com.sadps.security.kafka.SecurityEvent;
import com.sadps.security.kafka.SecurityEventProducer;
import com.sadps.security.redis.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final SecurityEventProducer securityEventProducer;
    @Autowired
    private final AuditService auditService;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MS = 30 * 60 * 1000;

    public String registerUser(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email Already Registered";
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .accountNonLocked(true)
                .failedAttempts(0)
                .lockTime(null)
                .build();

        userRepository.save(user);
        return "User Registered Successfully";
    }

    public String login(String email, String password) {

        if (loginAttemptService.isBlocked(email)) {
            securityEventProducer.publish(
                    SecurityEvent.builder()
                            .eventType("LOGIN_BLOCKED_REDIS")
                            .userEmail(email)
                            .build()
            );
            throw new UnauthorizedException("Too many login attempts. Try again later.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(password, user.getPassword())) {

            int attempts = loginAttemptService.increment(email);

            securityEventProducer.publish(
                    SecurityEvent.builder()
                            .eventType("LOGIN_FAILED")
                            .userEmail(email)
                            .build()
            );

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setAccountNonLocked(false);
                user.setLockTime(System.currentTimeMillis());
                userRepository.save(user);

                securityEventProducer.publish(
                        SecurityEvent.builder()
                                .eventType("ACCOUNT_LOCKED")
                                .userEmail(email)
                                .build()
                );
            }

            throw new UnauthorizedException("INVALID_CREDENTIALS");
        }


        loginAttemptService.reset(email);

    public String login(String email, String password){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UnauthorizedException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(password, user.getPassword())){
            user.setFailedAttempts(user.getFailedAttempts()+1);

            auditService.log("LOGIN_FAILED", email);

            if(user.getFailedAttempts() >= 5){
                user.setAccountNonLocked(false);
                user.setLockTime(System.currentTimeMillis());
                auditService.log("ACCOUNT_LOCKED", email);
            }
            userRepository.save(user);
            throw new UnauthorizedException("INVALID_CREDENTIALS");
        }

        if(!user.isAccountNonLocked()){
            auditService.log("LOGIN_BLOCKED_LOCKED_ACCOUNT",email);
            throw new UnauthorizedException("Account is locked");
        }
        user.setFailedAttempts(0);
        user.setLockTime(null);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        auditService.log("LOGIN_SUCCESS", email);
        return jwtService.generateToken(user);
    }

        securityEventProducer.publish(
                SecurityEvent.builder()
                        .eventType("LOGIN_SUCCESS")
                        .userEmail(email)
                        .build()
        );

        return "Login Successful. Token: " + token;
    }
}
