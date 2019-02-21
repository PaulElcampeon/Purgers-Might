package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeReqDto;
import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeResDto;
import com.purgersmight.purgersmightapp.enums.AttributeType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateAvatarAttribute {

    @Autowired
    private AvatarService avatarService;

    public UpdateAvatarAttributeResDto updateAttributes(UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto) {

        Avatar avatar = avatarService.getAvatarByUsername(updateAvatarAttributeReqDto.getUsername());

        if (checkIfAvatarHasEnoughPoints(avatar, updateAvatarAttributeReqDto.getCost())) {

            avatar.setKenjaPoints(avatar.getKenjaPoints() - updateAvatarAttributeReqDto.getCost());

            updateAvatar(avatar, updateAvatarAttributeReqDto.getAttributeType());

            return new UpdateAvatarAttributeResDto(true, avatar);

        } else {

            return new UpdateAvatarAttributeResDto(false, avatar);
        }
    }

    private boolean checkIfAvatarHasEnoughPoints(Avatar avatar, int cost) {

        return avatar.getKenjaPoints() >= cost;
    }

    private void updateAvatar(Avatar avatar, AttributeType attributeType) {

        if (attributeType == AttributeType.HEALTH) {

            avatar.getHealth().setActual(avatar.getHealth().getActual() + 10);
        }

        if (attributeType == AttributeType.MANNA) {

            avatar.getManna().setActual(avatar.getManna().getActual() + 5);
        }

        avatarService.updateAvatar(avatar);
    }
}
