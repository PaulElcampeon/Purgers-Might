package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.enums.AttackType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AttackPlayerReqDto {

    private String eventId;
    private AttackType attackType;
    private int spellPosition;

    public AttackPlayerReqDto() {
    }

    public AttackPlayerReqDto(AttackType attackType, int spellPosition) {
        this.attackType = attackType;
        this.spellPosition = spellPosition;
    }

}
