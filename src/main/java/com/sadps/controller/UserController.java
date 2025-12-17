package com.sadps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public String profile(){
        return "You are authenticated!";
    }
    @GetMapping("/secure")
    public String secure(){
        return "JWT authentication Works";
    }
}
