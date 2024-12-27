package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.models.User;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {

    User signup(String email,String password);
    User login(String email,String password);
    String loginForToken(String email,String password);

    Boolean validate(String token);
}
