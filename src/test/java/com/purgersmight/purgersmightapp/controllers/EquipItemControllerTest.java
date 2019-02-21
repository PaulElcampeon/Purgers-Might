package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.EquipItemReqDto;
import com.purgersmight.purgersmightapp.dto.EquipItemResDto;
import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.items.Armour;
import com.purgersmight.purgersmightapp.models.items.Weapon;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.utils.EquipItemUtil;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EquipItemController.class, secure = false)
public class EquipItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private EquipItemUtil equipItemUtil;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void equipItem_chest_Test1() throws Exception {

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Armour newArmour = new Armour("Metal Shell", 10, ArmourType.CHEST);
        avatar.getBag().getInventory().add(newArmour);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemReqDto);

        Avatar avatarResult = Avatar.getStarterAvatar("Angie");
        avatarResult.getBag().getInventory().add(avatarResult.getBodyArmour().getChestArmour());
        avatarResult.getBodyArmour().setChestArmour(newArmour);

        EquipItemResDto equipItemResDto = new EquipItemResDto();
        equipItemResDto.setSuccess(true);
        equipItemResDto.setAvatar(avatarResult);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemResDto);

        mockMvc.perform(put("/equip-item")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie");
    }

    @Test
    public void equipItem_head_Test2() throws Exception {

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Armour newArmour = new Armour("Turles Head", 10, ArmourType.HEAD);
        avatar.getBag().getInventory().add(newArmour);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemReqDto);

        Avatar avatarResult = Avatar.getStarterAvatar("Angie");
        avatarResult.getBag().getInventory().add(avatarResult.getBodyArmour().getHeadArmour());
        avatarResult.getBodyArmour().setHeadArmour(newArmour);

        EquipItemResDto equipItemResDto = new EquipItemResDto();
        equipItemResDto.setSuccess(true);
        equipItemResDto.setAvatar(avatarResult);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemResDto);

        mockMvc.perform(put("/equip-item")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie");
    }

    @Test
    public void equipItem_leg_Test3() throws Exception {

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Armour newArmour = new Armour("Wolfs Leggings", 10, ArmourType.LEG);
        avatar.getBag().getInventory().add(newArmour);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemReqDto);

        Avatar avatarResult = Avatar.getStarterAvatar("Angie");
        avatarResult.getBag().getInventory().add(avatarResult.getBodyArmour().getLegArmour());
        avatarResult.getBodyArmour().setLegArmour(newArmour);

        EquipItemResDto equipItemResDto = new EquipItemResDto();
        equipItemResDto.setSuccess(true);
        equipItemResDto.setAvatar(avatarResult);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemResDto);

        mockMvc.perform(put("/equip-item")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie");
    }

    @Test
    public void equipItem_weapon_Test4() throws Exception {

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        Weapon weapon = new Weapon("Metal Sword", 10, 5);
        avatar.getBag().getInventory().add(weapon);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        EquipItemReqDto equipItemReqDto = new EquipItemReqDto();
        equipItemReqDto.setUsername("Angie");
        equipItemReqDto.setIndexOfItemInBag(0);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemReqDto);

        Avatar avatarResult = Avatar.getStarterAvatar("Angie");
        avatarResult.getBag().getInventory().add(avatarResult.getWeapon());
        avatarResult.setWeapon(weapon);

        EquipItemResDto equipItemResDto = new EquipItemResDto();
        equipItemResDto.setSuccess(true);
        equipItemResDto.setAvatar(avatarResult);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(equipItemResDto);

        mockMvc.perform(put("/equip-item")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie");
    }


}
