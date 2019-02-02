package com.purgersmight.purgersmightapp;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "USERS")
public class User {

    @Id
    @NotNull(message = "username cannot be empty")
    private String username;

    @NotNull(message = "password cannot be empty")
    @Min(value = 5, message = "password must be at least 5 characters long")
    private String password;

    private String role = "USER";

}
