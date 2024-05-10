package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * User 관련 기능을 수행하는 FeignClient 인터페이스
 */
@FeignClient(value = "user-management", url = "${gateway.url}")
public interface UserAdapter {
    /**
     * 사용자 로그인
     *
     * @param loginRequest 로그인 요청 정보를 담은 객체
     * @return TokensResponse 객체, 로그인 결과에 대한 정보
     */
    @PostMapping(value = "/api/auth/login", headers = {"content-type=application/json", "accept=application/json"})
    TokensResponse doLogin(LoginRequest loginRequest, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    /**
     * 사용자 로그아웃
     *
     * @param refreshToken 로그아웃할 사용자의 리프레시 토큰
     */
    @PostMapping("/api/auth/logout")
    void doLogout(String refreshToken, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    /**
     * 사용자를 등록
     *
     * @param userRegisterRequest 사용자 등록 요청 정보를 담은 객체
     */
    @PostMapping("/api/user/register")
    void createUser(UserRegisterRequest userRegisterRequest, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    /**
     * 리프레시 토큰을 사용해 액세스 토큰 재발급
     *
     * @param refreshToken 재발급 액세스 토큰을 갖고 있는 리프레시 토큰
     * @return AccessTokenResponse 객체, 재발급된 액세스 토큰에 대한 정보 담기
     */
    @GetMapping("/api/auth/reissue")
    AccessTokenResponse reissue(@RequestHeader("X-REFRESH-TOKEN") String refreshToken);

    /**
     * 마이페이지 요청
     *
     * @param accessToken 인증된 사용자(Authorization) 가 가지고 있는 엑세스 토큰
     * @return UserDataResponse 객체, 마이페이지 구성을 위한 사용자 정보 DTO
     */
    @GetMapping("/api/user/myPage")
    UserDataResponse getUserData(@RequestHeader("Authorization") String accessToken);

    /**
     * 마이페이지 내 정보 수정 요청
     *
     * @param userUpdateRequest 객체, 수정할 사용자 정보를 담고있는 DTO
     * @param accessToken 인증된 사용자(Authorization) 가 가지고 있는 엑세스 토큰
     * @return UserDataResponse 객체, 메인 페이지 구성을 위한 사용자 정보 DTO
     */
    @PutMapping("/api/user/update")
    void updateUser(UserUpdateRequest userUpdateRequest, @RequestHeader("Authorization") String accessToken);


    @PostMapping("/api/user/deactivate")
    void deactivate(@RequestHeader("Authorization") String accessToken);
}
