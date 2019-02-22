package com.purgersmight.purgersmightapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChangeSpellReqDto {

    private String username;
    private int indexOfSpell;
    private String nameOfNewSpell;

    public ChangeSpellReqDto(){}
}
