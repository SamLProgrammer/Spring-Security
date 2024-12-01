package com.example.security.security.configurations;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.security.security.validations.TimeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private TimeInterceptor jsonValidatorInterceptor;

    public WebConfig(TimeInterceptor jsonValidatorInterceptor) {
        this.jsonValidatorInterceptor = jsonValidatorInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jsonValidatorInterceptor).addPathPatterns(Arrays.asList("/users/create"));
    }
}
