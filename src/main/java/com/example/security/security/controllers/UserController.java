package com.example.security.security.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.security.dto.CreateUserPayload;
import com.example.security.security.dto.UserWithRoles;
import com.example.security.security.models.User;
import com.example.security.security.services.UserService;
import com.example.security.security.validations.JSONValidator;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController()
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JSONValidator jsonValidator;

    public UserController(UserService userService, JSONValidator jsonValidator) {
        this.userService = userService;
        this.jsonValidator = jsonValidator;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserPayload createUserPayload) {
        return ResponseEntity.ok().body(userService.createUser(createUserPayload));
    }

    @GetMapping("/all")
    public ArrayList<UserWithRoles> getUsers() {
        return userService.getUsers();
    }
    
    @GetMapping
    public String getMethodName() {
        return "Welcome to Users Module";
    }
    
}
