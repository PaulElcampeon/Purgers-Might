package com.purgersmight.purgersmightapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateNewUserReqDto {

    @NotNull(message = "username cannot be empty")
    @Size(min = 5, max = 10, message = "username has to be between 5 and 10 characters long")
    private String username;

    @NotNull
    @Size(min = 5, max = 10, message = "password has to be between 5 and 10 characters long")
    private String password;

    @NotNull
    @Size(min = 5, max = 10, message = "password has to be between 5 and 10 characters long")
    private String confirmPassword;

    private String imageUrl;

    public CreateNewUserReqDto() {
    }

    public CreateNewUserReqDto(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
