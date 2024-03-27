package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.UserRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("user-management")
public interface UserAdaptor {
    @PostMapping("/register")
    void createUser(UserRegisterRequest userRegisterRequest);
}
