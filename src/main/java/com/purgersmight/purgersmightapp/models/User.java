package com.purgersmight.purgersmightapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Document(collection = "USERS")
public class User {

    @Id
    @NotNull(message = "username cannot be empty")
    private String username;
    @NotNull(message = "password cannot be empty")
    @Size(min = 5, max = 10, message = "password must be at least 5 characters long")
    private String password;

    private List<String> roles = Arrays.asList("USER");

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public static User getTesterUser(){
        return new User("angie", "127dh34");
    }
}
