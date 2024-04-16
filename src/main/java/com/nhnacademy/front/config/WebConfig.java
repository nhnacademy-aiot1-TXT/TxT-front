package com.nhnacademy.front.config;

import com.nhnacademy.front.interceptor.AddAuthorityToModelInterceptor;
import com.nhnacademy.front.interceptor.LoggedInUserAccessInterceptor;
import com.nhnacademy.front.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AddAuthorityToModelInterceptor addAuthorityToModelInterceptor;
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final LoggedInUserAccessInterceptor loggedInUserAccessInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/auth/register").setViewName("register");
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(30000))
                .setConnectTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addAuthorityToModelInterceptor);
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**","")
                .excludePathPatterns("/auth/login", "/login", "/register", "/auth/register", "/api/auth/register", "/css/**", "/js/**", "/img/**");
        registry.addInterceptor(loggedInUserAccessInterceptor)
                .addPathPatterns("/auth/login", "/auth/register");
    }
}
