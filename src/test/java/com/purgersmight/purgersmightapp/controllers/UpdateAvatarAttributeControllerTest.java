package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeReqDto;
import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeResDto;
import com.purgersmight.purgersmightapp.enums.AttributeType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import com.purgersmight.purgersmightapp.utils.UpdateAvatarAttribute;
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

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UpdateAvatarAttributeController.class, secure = false)
public class UpdateAvatarAttributeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UpdateAvatarAttribute updateAvatarAttribute;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void updateAvatarAttribute__Test1() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.HEALTH, 1, true);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.setKenjaPoints(2);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        Avatar updatedAvatar = Avatar.getStarterAvatar("Angie");
        updatedAvatar.setKenjaPoints(1);
        updatedAvatar.getHealth().setActual(110);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(true, updatedAvatar);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
    public void updateAvatarAttribute__Test2() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.MANNA, 1, true);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.setKenjaPoints(2);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        Avatar updatedAvatar = Avatar.getStarterAvatar("Angie");
        updatedAvatar.setKenjaPoints(1);
        updatedAvatar.getManna().setActual(65);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(true, updatedAvatar);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
    public void updateAvatarAttribute__Test3() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.MANNA, 1, true);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(false, avatar);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
    public void updateAvatarAttribute__Test4() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.MANNA, 0, false);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getHealth().setActual(100);
        avatar.getManna().setActual(60);
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        Avatar avatarRes = Avatar.getStarterAvatar("Angie");
        avatarRes.getHealth().setActual(100);
        avatarRes.getManna().setActual(55);
        avatarRes.setKenjaPoints(1);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(true, avatarRes);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
    public void updateAvatarAttribute__Test5() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.HEALTH, 0, false);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getHealth().setActual(100);
        avatar.getManna().setActual(60);
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        Avatar avatarRes = Avatar.getStarterAvatar("Angie");
        avatarRes.getHealth().setActual(90);
        avatarRes.getManna().setActual(60);
        avatarRes.setKenjaPoints(1);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(true, avatarRes);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
    public void updateAvatarAttribute__Test6() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.HEALTH, 0, false);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getHealth().setActual(50);
        avatar.getManna().setActual(60);
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        Avatar avatarRes = Avatar.getStarterAvatar("Angie");
        avatarRes.getHealth().setActual(50);
        avatarRes.getManna().setActual(60);
        avatarRes.setKenjaPoints(0);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(false, avatarRes);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
    public void updateAvatarAttribute__Test7() throws Exception {
        UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto = new UpdateAvatarAttributeReqDto("Angie", AttributeType.MANNA, 0, false);

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeReqDto);

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getHealth().setActual(50);
        avatar.getManna().setActual(60);
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);

        Avatar avatarRes = Avatar.getStarterAvatar("Angie");
        avatarRes.getHealth().setActual(50);
        avatarRes.getManna().setActual(55);
        avatarRes.setKenjaPoints(1);

        UpdateAvatarAttributeResDto updateAvatarAttributeResDto = new UpdateAvatarAttributeResDto(true, avatarRes);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(updateAvatarAttributeResDto);

        mockMvc.perform(put("/update-avatar-attribute")
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
