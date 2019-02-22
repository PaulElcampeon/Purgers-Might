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

            if (updateAvatarAttributeReqDto.isIncrease()) {

                avatar.setKenjaPoints(avatar.getKenjaPoints() - updateAvatarAttributeReqDto.getCost());

            } else {

                if (avatar.getHealth().getActual().intValue() > 50 && updateAvatarAttributeReqDto.getAttributeType() == AttributeType.HEALTH
                        || avatar.getManna().getActual().intValue() > 30 && updateAvatarAttributeReqDto.getAttributeType() == AttributeType.MANNA) {

                    avatar.setKenjaPoints(avatar.getKenjaPoints() + 1);

                } else {

                    return new UpdateAvatarAttributeResDto(false, avatar);

                }

            }
            updateAvatar(avatar, updateAvatarAttributeReqDto.getAttributeType(), updateAvatarAttributeReqDto.isIncrease());

            return new UpdateAvatarAttributeResDto(true, avatar);

        } else {

            return new UpdateAvatarAttributeResDto(false, avatar);
        }
    }

    private boolean checkIfAvatarHasEnoughPoints(Avatar avatar, int cost) {

        return avatar.getKenjaPoints() >= cost;
    }

    private void updateAvatar(Avatar avatar, AttributeType attributeType, boolean isIncrease) {

        if (attributeType == AttributeType.HEALTH) {

            if (isIncrease) {

                avatar.getHealth().setActual(avatar.getHealth().getActual() + 10);

            } else {

                avatar.getHealth().setActual(avatar.getHealth().getActual() - 10);
            }
        }

        if (attributeType == AttributeType.MANNA) {

            if (isIncrease) {

                avatar.getManna().setActual(avatar.getManna().getActual() + 5);

            } else {

                avatar.getManna().setActual(avatar.getManna().getActual() - 5);
            }
        }

        avatarService.updateAvatar(avatar);
    }
}
