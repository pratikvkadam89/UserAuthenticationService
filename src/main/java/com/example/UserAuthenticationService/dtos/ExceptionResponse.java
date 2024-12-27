package com.example.UserAuthenticationService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private String message;
    private String errorCode;
}
