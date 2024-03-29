package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.LoginRequest;
import com.nhnacademy.front.dto.UserRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;


@FeignClient(value = "user-management", url = "${gateway.url}")
public interface UserAdaptor {
    @PostMapping("/api/auth/login")
    Map<String, Object> doLogin(LoginRequest loginRequest);
    @PostMapping("/api/user/register")
    void createUser(UserRegisterRequest userRegisterRequest);
}
