package com.sadps.controller;


import com.sadps.dto.LoginRequest;
import com.sadps.dto.SignupRequest;
import com.sadps.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest){
        return authService.registerUser(signupRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        return authService.login(request.getEmail(), request.getPassword());
    }
}
