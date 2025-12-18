package com.sadps.services;

import com.sadps.entity.User;
import com.sadps.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not Found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
