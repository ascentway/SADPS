package com.sadps.services;

import com.sadps.dto.ChangePasswordRequest;
import com.sadps.dto.UserResponseDto;
import com.sadps.entity.User;
import com.sadps.exceptions.UnauthorizedException;
import com.sadps.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    public User getCurrentUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not Found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void changePassword(String email, ChangePasswordRequest request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UnauthorizedException("User not Found"));

        if(!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            auditService.log("PASSWORD_CHANGE_FAILED",email);
            throw
                    new UnauthorizedException(("Current Password is Incorrect"));
        }

        user.setPassword((passwordEncoder.encode(request.getNewPassword())));
        userRepository.save(user);
        auditService.log("PASSWORD_CHANGED", email);
    }

}
