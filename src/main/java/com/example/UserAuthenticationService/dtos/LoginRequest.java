package com.example.UserAuthenticationService.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String emailId;
    private String password;
}
