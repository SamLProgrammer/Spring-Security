package com.example.security.security.dto;

import java.util.ArrayList;
import com.example.security.security.models.User;

public class CreateUserPayload {
    
    private User user;
    private ArrayList<String> roles;

    public ArrayList<String> getRoles() {
        return roles;
    }

    public User getUser() {
        return user;
    }
}
