package com.zj.examsystem.config.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest
{
    // 1-stu 2-tch 3-mgr
    private Integer identity;

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
