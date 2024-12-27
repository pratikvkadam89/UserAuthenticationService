package com.example.UserAuthenticationService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "my_user")
public class User extends BaseModel{

    private String emailId;
    private String password;
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

}
