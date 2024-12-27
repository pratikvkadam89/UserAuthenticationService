package com.example.UserAuthenticationService.controller;


import com.example.UserAuthenticationService.dtos.LoginRequest;
import com.example.UserAuthenticationService.dtos.SignUpRequest;
import com.example.UserAuthenticationService.dtos.UserDto;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    IAuthService authService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequest request){
        User user = authService.signup(request.getEmailId(),request.getPassword());
        return from(user);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequest request){
        User user = authService.login(request.getEmailId(),request.getPassword());
        return from(user);
    }

    @PostMapping("/loginForToken")
    public ResponseEntity<Object> loginForToken(@RequestBody LoginRequest request){
       String token = authService.loginForToken(request.getEmailId(), request.getPassword());
       Map<String,String> body = new HashMap<>();
       body.put("message","Login Success");
       HttpHeaders headers = new HttpHeaders();
       headers.add("Token",token);
       ResponseEntity<Object> responseEntity = new ResponseEntity(body,headers, HttpStatus.OK);
       return responseEntity;
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam("token") String token) {
        System.out.println("Here I am");
//        return false;
        return authService.validate(token);
    }

    private UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmailId(user.getEmailId());
        userDto.setRoleList(user.getRoleList());
        return userDto;
    }


}
