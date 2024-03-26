package com.nhnacademy.front.adaptor.impl;

import com.nhnacademy.front.adaptor.UserAdaptor;
import com.nhnacademy.front.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserAdaptorImpl implements UserAdaptor {
    private final RestTemplate restTemplate;
    @Override
    public void createUser(UserRegisterRequest userRegisterRequest) {

    }
}
