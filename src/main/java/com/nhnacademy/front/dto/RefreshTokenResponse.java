package com.nhnacademy.front.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 리프레쉬 토큰 관련 정보를 저장하기 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenResponse {
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expire_in")
    private Integer expiresIn;
}
