package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.models.PvpEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetPvpEventResDto {

    private boolean success;
    private PvpEvent pvpEvent;

    public GetPvpEventResDto(){}


}
