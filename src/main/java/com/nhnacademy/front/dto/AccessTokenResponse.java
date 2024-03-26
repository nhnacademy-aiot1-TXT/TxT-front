package com.nhnacademy.front.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenResponse {
    private String accessToken;
    private String tokenType;
    private String expireIn;
}
