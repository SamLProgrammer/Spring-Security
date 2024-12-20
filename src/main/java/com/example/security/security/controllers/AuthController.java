package com.example.security.security.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.security.dto.LoginUser;
import com.example.security.security.filters.JWTAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUser user) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            // Generate JWT token
            org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) authentication
                    .getPrincipal();
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            Claims claims = Jwts.claims().add("authorities", roles).build();

            String token = Jwts.builder()
                    .subject(authenticatedUser.getUsername())
                    .claims(claims)
                    .signWith(JWTAuthenticationFilter.SIGNATURE_KEY)
                    .expiration(new Date(System.currentTimeMillis() + 3600000))
                    .issuedAt(new Date())
                    .compact();

            // Response body
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", authenticatedUser.getUsername());
            response.put("message",
                    String.format("Welcome %s, you have been authenticated!", authenticatedUser.getUsername()));

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials", "message", ex.getMessage()));
        }
    }
}
