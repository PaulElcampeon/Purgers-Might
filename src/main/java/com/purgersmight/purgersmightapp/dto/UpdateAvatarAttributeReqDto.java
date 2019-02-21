package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.enums.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateAvatarAttributeReqDto {

    private String username;
    private AttributeType attributeType;
    private int cost;

    public UpdateAvatarAttributeReqDto(){}
}
