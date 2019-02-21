package com.purgersmight.purgersmightapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RemoveFromPvpQueueReqDto {

    public String username;

    public RemoveFromPvpQueueReqDto() {
    }
}
