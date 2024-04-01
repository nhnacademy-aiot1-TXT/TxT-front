package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.LoginRequest;
import com.nhnacademy.front.dto.TokensResponse;
import com.nhnacademy.front.dto.UserRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "user-management", url = "${gateway.url}")
public interface UserAdapter {
    @PostMapping(value = "/api/auth/login", headers = {"content-type=application/json", "accept=application/json"})
    TokensResponse doLogin(LoginRequest loginRequest, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    @PostMapping("/api/auth/logout")
    void doLogout(String refreshToken, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    @PostMapping("/api/user/register")
    void createUser(UserRegisterRequest userRegisterRequest, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    @GetMapping("/api/auth/reissue")
    AccessTokenResponse reissue(@RequestHeader("X-REFRESH-TOKEN") String refreshToken);
}
