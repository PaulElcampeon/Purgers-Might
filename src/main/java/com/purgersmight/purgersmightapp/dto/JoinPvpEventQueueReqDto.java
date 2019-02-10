package com.purgersmight.purgersmightapp.dto;

import lombok.Data;

@Data
public class JoinPvpEventQueueReqDto {

    private String username;

    public JoinPvpEventQueueReqDto(){}

    public JoinPvpEventQueueReqDto(String username){
        this.username = username;
    }
}
