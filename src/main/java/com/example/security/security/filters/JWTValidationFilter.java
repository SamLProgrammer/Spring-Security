package com.example.security.security.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authHeader = request.getHeader("authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = Jwts.parser().verifyWith(JWTAuthenticationFilter.SIGNATURE_KEY).build()
                    .parseSignedClaims(token).getPayload();

            List<?> authoritiesList = (List<?>) claims.get("authorities");

            Collection<GrantedAuthority> authorities = authoritiesList.stream()
                    .map(authority -> new SimpleGrantedAuthority(((Map<?, ?>) authority).get("authority").toString()))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken UPToken = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                    null, authorities);
            SecurityContextHolder.getContext().setAuthentication(UPToken);
        }
        chain.doFilter(request, response);
    }

}
