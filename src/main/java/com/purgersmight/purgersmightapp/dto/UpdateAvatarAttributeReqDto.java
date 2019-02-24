package com.purgersmight.purgersmightapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.purgersmight.purgersmightapp.enums.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateAvatarAttributeReqDto {

    private String username;
    private AttributeType attributeType;
    private int cost;
    @JsonProperty
    private boolean isIncrease;


    public UpdateAvatarAttributeReqDto(){}
}
