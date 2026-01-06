package com.sadps.services;

import com.sadps.entity.User;
import com.sadps.exceptions.UnauthorizedException;
import com.sadps.respository.UserRepository;
import com.sadps.security.jwt.JwtService;
import com.sadps.security.kafka.SecurityEvent;
import com.sadps.security.kafka.SecurityEventProducer;
import com.sadps.security.redis.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sadps.dto.SignupRequest;
import com.sadps.security.Role;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final SecurityEventProducer securityEventProducer;
    private final AuditService auditService;public String registerUser(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UnauthorizedException("Email already registered");
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

        auditService.log("USER_REGISTERED", request.getEmail());

        return "User registered successfully";
    }


    private static final int MAX_FAILED_ATTEMPTS = 5;



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

            auditService.log("LOGIN_FAILED", email);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setAccountNonLocked(false);
                user.setLockTime(System.currentTimeMillis());
                auditService.log("ACCOUNT_LOCKED", email);
                userRepository.save(user);
            }

            securityEventProducer.publish(
                    SecurityEvent.builder()
                            .eventType("LOGIN_FAILED")
                            .userEmail(email)
                            .build()
            );

            throw new UnauthorizedException("INVALID_CREDENTIALS");
        }

        if (!user.isAccountNonLocked()) {
            auditService.log("LOGIN_BLOCKED_LOCKED_ACCOUNT", email);
            throw new UnauthorizedException("Account is locked");
        }


        loginAttemptService.reset(email);
        user.setFailedAttempts(0);
        user.setLockTime(null);
        userRepository.save(user);

        securityEventProducer.publish(
                SecurityEvent.builder()
                        .eventType("LOGIN_SUCCESS")
                        .userEmail(email)
                        .build()
        );

        auditService.log("LOGIN_SUCCESS", email);

        return jwtService.generateToken(user);
    }
}
