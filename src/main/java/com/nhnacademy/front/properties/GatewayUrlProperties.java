package com.nhnacademy.front.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway의 URL을 설정하는 properties 클래스
 */
@Configuration
@ConfigurationProperties(prefix = "gateway")
@Getter
@Setter
public class GatewayUrlProperties {
    /**
     * Gateway URL을 나타내는 문자열
     */
    private String url;
}
