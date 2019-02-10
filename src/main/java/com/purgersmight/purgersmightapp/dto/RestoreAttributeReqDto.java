package com.purgersmight.purgersmightapp.dto;

import lombok.Data;

@Data
public class RestoreAttributeReqDto {

    private String username;

    public RestoreAttributeReqDto(){}

    public RestoreAttributeReqDto(String username){
        this.username = username;
    }
}
