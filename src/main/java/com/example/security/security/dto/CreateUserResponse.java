package com.example.security.security.dto;

import com.example.security.security.models.User;

public class CreateUserResponse {
    private User user;
    private String error;

    public User getUser() {
        return user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
