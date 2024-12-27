package com.example.UserAuthenticationService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String emailId;
    private String password;
}
