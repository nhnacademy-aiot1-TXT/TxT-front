package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.page.RestPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 관현 API 요청을 보내기 위한 adapter 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@FeignClient(value = "user-management", url = "${gateway.url}")
public interface UserAdapter {
    /**
     * 사용자 로그인
     *
     * @param loginRequest 로그인 요청 정보를 담은 객체
     * @param csrfToken    the csrf token
     * @return TokensResponse 객체, 로그인 결과에 대한 정보
     */
    @PostMapping(value = "/api/auth/login", headers = {"content-type=application/json", "accept=application/json"})
    TokensResponse doLogin(LoginRequest loginRequest, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    /**
     * 사용자 로그아웃
     *
     * @param refreshToken 로그아웃할 사용자의 리프레시 토큰
     * @param csrfToken    the csrf token
     */
    @PostMapping("/api/auth/logout")
    void doLogout(String refreshToken, @RequestHeader("X-CSRF-TOKEN") String csrfToken);

    /**
     * 사용자를 등록
     *
     * @param userRegisterRequest 사용자 등록 요청 정보를 담은 객체
     * @param csrfToken           the csrf token
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
     * @param accessToken       인증된 사용자(Authorization) 가 가지고 있는 엑세스 토큰
     * @return UserDataResponse 객체, 메인 페이지 구성을 위한 사용자 정보 DTO
     */
    @PutMapping("/api/user/update")
    void updateUser(UserUpdateRequest userUpdateRequest, @RequestHeader("Authorization") String accessToken);

    /**
     * 사용자의 가입을 허용하는 메서드
     *
     * @param accessToken           the access token
     * @param permitUserRequestList the permit user request list
     */
    @PostMapping("/api/user/admin/permit")
    void permitUser(@RequestHeader("Authorization") String accessToken,
                    @RequestBody List<PermitUserRequest> permitUserRequestList);

    /**
     * 사용자를 관리자로 변환하는 메서드
     *
     * @param accessToken           the access token
     * @param permitUserRequestList the permit user request list
     */
    @PostMapping("/api/user/admin/promotion")
    void promotionUser(@RequestHeader("Authorization") String accessToken,
                       @RequestBody List<PermitUserRequest> permitUserRequestList);

    /**
     * 사용자를 비활성화하는 메서드
     *
     * @param accessToken the access token
     */
    @PostMapping("/api/user/deactivate")
    void deactivate(@RequestHeader("Authorization") String accessToken);

    /**
     * 주어진 상태와 일치하는 사용자를 정렬하여 조회하는 메서드
     *
     * @param accessToken the access token
     * @param statusId    the status id
     * @param page        the page
     * @param size        the size
     * @return the rest page
     */
    @GetMapping("/api/user/admin/userList/sort/status/{statusId} ")
    RestPage<UserDataResponse> findSortedUsers(@RequestHeader("Authorization") String accessToken,
                                               @PathVariable int statusId,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size);

    /**
     * 사용자들을 조회하는 메서드
     *
     * @param accessToken the access token
     * @param page        the page
     * @param size        the size
     * @return the rest page
     */
    @GetMapping("/api/user/admin/userList")
    RestPage<UserDataResponse> findAllUsers(@RequestHeader("Authorization") String accessToken,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size);

    /**
     * 주어진 권한과 일치하는 사용자를 정렬하여 조회하는 메서드
     *
     * @param accessToken the access token
     * @param page        the page
     * @param size        the size
     * @param roleId      the role id
     * @return the rest page
     */
    @GetMapping("/api/user/admin/userList/sort/role/{roleId}")
    RestPage<UserDataResponse> findSortedUserByRole(@RequestHeader("Authorization") String accessToken,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @PathVariable int roleId);


}
