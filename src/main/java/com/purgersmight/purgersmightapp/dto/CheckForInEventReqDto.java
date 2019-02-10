package com.purgersmight.purgersmightapp.dto;

import lombok.Data;

@Data
public class CheckForInEventReqDto {

    private String username;

    public CheckForInEventReqDto(){}

    public CheckForInEventReqDto(String username){
        this.username = username;
    }
}
