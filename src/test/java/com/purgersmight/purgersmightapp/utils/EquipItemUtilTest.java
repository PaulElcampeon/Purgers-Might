package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.EquipItemReqDto;
import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.items.Armour;
import com.purgersmight.purgersmightapp.models.items.Weapon;
import com.purgersmight.purgersmightapp.services.AvatarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EquipItemUtilTest {

    @Autowired
    private EquipItemUtil equipItemUtil;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void equipWeapon__Test1() {
        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Weapon weapon = new Weapon("Metal Sword", 10, 5);
        Weapon currentlyEquipped = avatar.getWeapon();
        avatar.getBag().getInventory().add(weapon);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        equipItemUtil.equipItem(equipItemReqDto);

        assertEquals(currentlyEquipped, avatar.getBag().getInventory().get(0));

        assertEquals("Metal Sword", avatar.getWeapon().getName());
    }

    @Test
    public void equipArmour_chest_Test2() {
        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Armour armour = new Armour("Metal Shell", 10, ArmourType.CHEST);
        Armour currentlyEquipped = avatar.getBodyArmour().getChestArmour();
        avatar.getBag().getInventory().add(armour);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        equipItemUtil.equipItem(equipItemReqDto);

        assertEquals(currentlyEquipped, avatar.getBag().getInventory().get(0));

        assertEquals("Metal Shell", avatar.getBodyArmour().getChestArmour().getName());
    }

    @Test
    public void equipArmour_head_Test3() {
        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Armour armour = new Armour("Metal Helm", 10, ArmourType.HEAD);
        Armour currentlyEquipped = avatar.getBodyArmour().getHeadArmour();
        avatar.getBag().getInventory().add(armour);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        equipItemUtil.equipItem(equipItemReqDto);

        assertEquals(currentlyEquipped, avatar.getBag().getInventory().get(0));

        assertEquals("Metal Helm", avatar.getBodyArmour().getHeadArmour().getName());
    }

    @Test
    public void equipArmour_leg_Test4() {
        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Armour armour = new Armour("Metal Leggings", 10, ArmourType.LEG);
        Armour currentlyEquipped = avatar.getBodyArmour().getLegArmour();
        avatar.getBag().getInventory().add(armour);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        equipItemUtil.equipItem(equipItemReqDto);

        assertEquals(currentlyEquipped, avatar.getBag().getInventory().get(0));

        assertEquals("Metal Leggings", avatar.getBodyArmour().getLegArmour().getName());
    }

}
