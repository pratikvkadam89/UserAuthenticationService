package com.example.UserAuthenticationService.dtos;

import com.example.UserAuthenticationService.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private String emailId;
    private List<Role> roleList;
}
