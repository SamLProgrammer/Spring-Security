package com.example.security.security.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.security.dto.CreateUserPayload;
import com.example.security.security.dto.UserWithRoles;
import com.example.security.security.services.UserService;
import com.example.security.security.utilities.AuthoritiesManager;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController()
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserPayload createUserPayload, Authentication authentication) {
        return ResponseEntity.ok().body(userService.createUser(AuthoritiesManager.extractRoles(authentication), createUserPayload));
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
