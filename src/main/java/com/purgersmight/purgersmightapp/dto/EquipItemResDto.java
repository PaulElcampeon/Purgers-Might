package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.models.Avatar;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EquipItemResDto {

    private boolean success;
    private Avatar avatar;

    public EquipItemResDto(){}

}
