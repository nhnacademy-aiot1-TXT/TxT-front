package com.nhnacademy.front.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
