package com.example.security.security.validations;

import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (request.getMethod().equals("POST")) {
            System.out.println("Intercepting POST on: " + request.getPathInfo() + " at: " + (new Date()).getTime());
        }
        
        return true;
    }

}
