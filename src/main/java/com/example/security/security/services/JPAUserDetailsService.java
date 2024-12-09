package com.example.security.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.security.models.User;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private UserService userService;

    public JPAUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user != null) {
            authorities = userService.queryRolesByUsername(username).stream()
            .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(
        user.getUsername(), 
        user.getPassword(), 
        user.isActive(), 
        true, 
        true, 
        true, 
        authorities);
    }
    
}
