package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.LoginRequest;

public interface LoginAdaptor {
    AccessTokenResponse doLogin(LoginRequest loginRequest);
}
