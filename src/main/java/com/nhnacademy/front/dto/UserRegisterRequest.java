package com.nhnacademy.front.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 등록 요청을 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Getter
@Setter
public class UserRegisterRequest {
    @NotBlank(message = "아이디가 입력되지 않았습니다.")
    private String id;
    @NotBlank(message = "이름이 입력되지 않았습니다.")
    private String name;
    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    private String email;
    @NotBlank(message = "생일이 입력되지 않았습니다.")
    private String birth;
}
