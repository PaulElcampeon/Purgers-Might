package com.purgersmight.purgersmightapp;

import com.purgersmight.purgersmightapp.models.Avatar;
import lombok.Data;

@Data
public class CreateNewUserResDto {

    private boolean success;
    private String error;
    private Avatar avatar;

}
