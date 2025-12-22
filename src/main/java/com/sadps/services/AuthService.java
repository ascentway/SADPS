package com.sadps.services;

import com.sadps.dto.SignupRequest;
import com.sadps.entity.User;
import com.sadps.exceptions.UnauthorizedException;
import com.sadps.respository.UserRepository;
import com.sadps.security.Role;
import com.sadps.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuditService auditService;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MS = 30 * 60 * 1000;

    public String registerUser(SignupRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
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

        auditService.log("LOGIN_SUCCESS", email);
        return jwtService.generateToken(user);
    }

}