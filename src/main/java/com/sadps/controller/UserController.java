package com.sadps.controller;

import com.sadps.dto.ChangePasswordRequest;
import com.sadps.entity.User;
import com.sadps.services.UserService;
import com.sadps.security.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // USER + ADMIN
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User getMyProfile() {
        String email = SecurityUtils.getCurrentUserEmail();
        return userService.getCurrentUser(email);
    }

    // ADMIN ONLY
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        String email = SecurityUtils.getCurrentUserEmail();
        userService.changePassword(
               email, request
        );
        return ResponseEntity.ok("Password Updated Successfully");
    }
}
