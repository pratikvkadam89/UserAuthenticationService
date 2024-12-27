package com.example.UserAuthenticationService.exceptions;

public class UserNotPresent extends RuntimeException {
    public UserNotPresent(String pleaseSignupFirst) {
        super(pleaseSignupFirst);
    }
}
