package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.dto.EquipItemReqDto;
import com.purgersmight.purgersmightapp.dto.EquipItemResDto;
import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.items.Armour;
import com.purgersmight.purgersmightapp.models.items.Item;
import com.purgersmight.purgersmightapp.models.items.Weapon;
import com.purgersmight.purgersmightapp.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Scope(value = "singleton")
public class EquipItemUtil {

    @Autowired
    private AvatarService avatarService;

    private Logger logger = Logger.getLogger(EquipItemUtil.class.getName());

    public EquipItemResDto equipItem(EquipItemReqDto equipItemReqDto) {

        Avatar avatar = avatarService.getAvatarByUsername(equipItemReqDto.getUsername());

        Item item = avatar.getBag().getInventory().get(equipItemReqDto.getIndexOfItemInBag());

        if(item instanceof Weapon){

            return equipWeapon(avatar, item);

        } else if(item instanceof Armour) {

            return equipArmour(avatar, item);
        }

        logger.log(Level.INFO, String.format("%s tried to equip an item that is not a weapon or armour", avatar.getUsername()));

        return new EquipItemResDto(false, avatar);
    }

    private EquipItemResDto equipWeapon(Avatar avatar, Item item) {

        Weapon weapon = (Weapon)item;

        avatar.getBag().getInventory().remove(item);

        avatar.getBag().getInventory().add(avatar.getWeapon());

        avatar.setWeapon(weapon);

        avatarService.updateAvatar(avatar);

        logger.log(Level.INFO, String.format("%s equipped a new weapon", avatar.getUsername()));

        return new EquipItemResDto(true, avatar);
    }

    private EquipItemResDto equipArmour(Avatar avatar, Item item) {

        Armour armourInc = (Armour)item;

        if(armourInc.getArmourType() == ArmourType.CHEST) {

            avatar.getBag().getInventory().add(avatar.getBodyArmour().getChestArmour());

            avatar.getBag().getInventory().remove(armourInc);

            avatar.getBodyArmour().setChestArmour(armourInc);
        }

        if(armourInc.getArmourType() == ArmourType.HEAD) {

            avatar.getBag().getInventory().add(avatar.getBodyArmour().getHeadArmour());

            avatar.getBag().getInventory().remove(armourInc);

            avatar.getBodyArmour().setHeadArmour(armourInc);        }

        if(armourInc.getArmourType() == ArmourType.LEG) {

            avatar.getBag().getInventory().add(avatar.getBodyArmour().getLegArmour());

            avatar.getBag().getInventory().remove(armourInc);

            avatar.getBodyArmour().setLegArmour(armourInc);
        }

        avatarService.updateAvatar(avatar);

        logger.log(Level.INFO, String.format("%s equipped a new piece of armour", avatar.getUsername()));

        return new EquipItemResDto(true, avatar);
    }
}
