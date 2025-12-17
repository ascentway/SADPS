package com.sadps.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email Required")
    private String email;

    @NotBlank(message = "Password Required")
    private String password;
}
