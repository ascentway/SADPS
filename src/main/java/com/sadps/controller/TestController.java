package com.sadps.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

    @GetMapping("/user/test")
    @PreAuthorize("hasRole('USER')")
    public String userAccess(){
        return "USER Access Granted.";
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "ADMIN Access Granted.";
    }
}
