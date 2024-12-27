package com.example.UserAuthenticationService.exceptions;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword(String passwordIsIncorrect) {
        super(passwordIsIncorrect);
    }
}
