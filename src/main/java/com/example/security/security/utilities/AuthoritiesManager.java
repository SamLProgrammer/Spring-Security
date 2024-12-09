package com.example.security.security.utilities;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;

public class AuthoritiesManager {

    public static ArrayList<String> extractRoles(Authentication authentication) {
        return (authentication != null) ? authentication.getAuthorities().stream().map((auth) -> auth.getAuthority())
                .collect(Collectors.toCollection(ArrayList::new)) : new ArrayList<String>();
    }
}
