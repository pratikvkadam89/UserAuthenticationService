package com.example.UserAuthenticationService.controller;

import com.example.UserAuthenticationService.dtos.ExceptionResponse;
import com.example.UserAuthenticationService.exceptions.InvalidPassword;
import com.example.UserAuthenticationService.exceptions.UserAlreadyExistsException;
import com.example.UserAuthenticationService.exceptions.UserNotPresent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(value = {InvalidPassword.class, UserAlreadyExistsException.class, UserNotPresent.class})
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.ok(exceptionResponse);
    }
}
