package com.sadps.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class UserResponseDto {

    private String email;
    private String fullName;
    private String role;


}
