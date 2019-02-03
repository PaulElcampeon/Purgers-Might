package com.purgersmight.purgersmightapp;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateNewUserReqDto {

    @NotNull
    @Min(value = 5)
    private String username;

    @NotNull
    @Min(value = 5)
    private String password;

    @NotNull
    @Min(value = 5)
    private String confirmPassword;
}
