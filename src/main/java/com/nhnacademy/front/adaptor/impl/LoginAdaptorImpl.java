package com.nhnacademy.front.adaptor.impl;

import com.nhnacademy.front.adaptor.LoginAdaptor;
import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.LoginRequest;
import com.nhnacademy.front.properties.GatewayUrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginAdaptorImpl implements LoginAdaptor {
    private final RestTemplate restTemplate;
    private final GatewayUrlProperties gatewayUrl;
    @Override
    public AccessTokenResponse doLogin(LoginRequest loginRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, httpHeaders);

        ResponseEntity<AccessTokenResponse> responseEntity = restTemplate.exchange(gatewayUrl + "/login",
                HttpMethod.POST,
                requestEntity,
                AccessTokenResponse.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException();
        }

        return responseEntity.getBody();
    }
}
