package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeReqDto;
import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeResDto;
import com.purgersmight.purgersmightapp.enums.AttributeType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UpdateAvatarAttributeTest {

    @Autowired
    private UpdateAvatarAttribute updateAvatarAttribute;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void updateAvatarAttribute_UpdateHealthReturnTrue_Test1() {

        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie1", AttributeType.HEALTH, 1);

        Avatar avatar = Avatar.getStarterAvatar("Angie1");
        avatar.setKenjaPoints(2);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        UpdateAvatarAttributeResDto result = updateAvatarAttribute.updateAttributes(updateAvatarAttributeReqDto);

        assertTrue(result.isSuccess());

        assertEquals(1,result.getAvatar().getKenjaPoints());

        assertEquals(110, avatar.getHealth().getActual().intValue());
    }

    @Test
    public void updateAvatarAttribute_UpdateMannaReturnTrue_Test2() {

        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie1", AttributeType.MANNA, 2);

        Avatar avatar = Avatar.getStarterAvatar("Angie1");
        avatar.setKenjaPoints(3);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        UpdateAvatarAttributeResDto result = updateAvatarAttribute.updateAttributes(updateAvatarAttributeReqDto);

        assertTrue(result.isSuccess());

        assertEquals(1,result.getAvatar().getKenjaPoints());

        assertEquals(65, avatar.getManna().getActual().intValue());
    }

    @Test
    public void updateAvatarAttribute_ReturnFalse_Test3() {

        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie1", AttributeType.HEALTH, 1);

        Avatar avatar = Avatar.getStarterAvatar("Angie1");
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        UpdateAvatarAttributeResDto result = updateAvatarAttribute.updateAttributes(updateAvatarAttributeReqDto);

        assertFalse(result.isSuccess());

        assertEquals(0,result.getAvatar().getKenjaPoints());

        assertEquals(100, avatar.getHealth().getActual().intValue());
    }
}
