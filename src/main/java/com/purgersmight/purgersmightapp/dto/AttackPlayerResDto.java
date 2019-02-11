package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.models.PvpEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AttackPlayerResDto {

    private boolean ended;
    private String winner;
    private PvpEvent pvpEvent;

    public AttackPlayerResDto(){}

}
