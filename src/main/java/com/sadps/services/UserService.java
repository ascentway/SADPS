package com.sadps.services;

import com.sadps.entity.User;
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

    public void changePassword(String email, String currentPassword, String newPassword){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not Found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new AccessDeniedException("Current Password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        auditService.log("PASSWORD_CHANGE",email);
    }


}
