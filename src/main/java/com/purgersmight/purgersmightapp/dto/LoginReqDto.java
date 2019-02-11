package com.purgersmight.purgersmightapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginReqDto {

    private String username;
    private String password;

    public LoginReqDto() {
    }
}
