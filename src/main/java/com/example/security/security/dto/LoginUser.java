package com.example.security.security.dto;

public class LoginUser {
    private String username;
    private String password;


    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
