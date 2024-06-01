package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "아이디가 입력되지 않았습니다.")
    String id;
    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    String password;
}
