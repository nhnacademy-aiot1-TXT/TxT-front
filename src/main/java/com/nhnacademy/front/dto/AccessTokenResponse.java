package com.nhnacademy.front.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


/**
 * AccessTokenResponse 클래스는 액세스 토큰 응답을 나타냅니다.
 * 액세스 토큰 응답은 액세스 토큰, 토큰 타입 및 만료 시간을 포함합니다.
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expire_in")
    private Integer expiresIn;
}
