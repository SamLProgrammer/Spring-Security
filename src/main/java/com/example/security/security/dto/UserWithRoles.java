package com.example.security.security.dto;

import com.example.security.security.models.User;

public class UserWithRoles {

    private User user;
    private String[] roles;



    public UserWithRoles(User user, String[] roles) {
        this.user = user;
        this.roles = roles;
    }


    public String[] getRoles() {
        return roles;
    }

    public User getUser() {
        return user;
    }
}
