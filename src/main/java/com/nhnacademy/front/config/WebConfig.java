package com.nhnacademy.front.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * 웹 설정 구성 클래스
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * ViewController 등록
     *
     * @param registry ViewControllerRegistry 객체
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/auth/login").setViewName("login");
    }

    /**
     * RestTemplate 빈 생성
     *
     * @param restTemplateBuilder RestTemplateBuilder 객체
     * @return RestTemplate 객체
     */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(30000))
                .setConnectTimeout(Duration.ofMillis(30000))
                .build();
    }
}
