package com.sadps.services;

import com.sadps.dto.SignupRequest;
import com.sadps.entity.User;
import com.sadps.respository.UserRepository;
import com.sadps.security.Role;
import com.sadps.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                .orElse(null);

        if (user == null){
            return "Invalid Credentials";
        }

        if (!user.isAccountNonLocked()){
            if(user.getLockTime() != null){
                long currentTime = System.currentTimeMillis();
                if (currentTime - user.getLockTime() < LOCK_DURATION_MS){
                    return "Account is Locked. Try Again Later.";
                }

                user.setAccountNonLocked(true);
                user.setFailedAttempts(0);
                user.setLockTime(null);
            }
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            user.setFailedAttempts(user.getFailedAttempts() +1 );

            if(user.getFailedAttempts() >= MAX_FAILED_ATTEMPTS){
                user.setAccountNonLocked(false);
                user.setLockTime(System.currentTimeMillis());
            }

            userRepository.save(user);
            return "Invalid Credentials";
        }

        user.setFailedAttempts(0);
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return "Login Successful. Token:" +token;
    }

}