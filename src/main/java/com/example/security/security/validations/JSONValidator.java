package com.example.security.security.validations;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.security.security.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JSONValidator {

    public boolean isValidUser(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue(json, User.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
