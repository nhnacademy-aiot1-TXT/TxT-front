package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "user-management", url = "${gateway.url}")
public interface LoginAdaptor {
    @PostMapping("/login")
    AccessTokenResponse doLogin(LoginRequest loginRequest);
}
