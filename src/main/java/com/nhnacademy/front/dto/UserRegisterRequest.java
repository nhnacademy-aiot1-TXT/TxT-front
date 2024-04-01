package com.nhnacademy.front.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String id;
    private String name;
    private String password;
    private String email;
    private String birth;
}
