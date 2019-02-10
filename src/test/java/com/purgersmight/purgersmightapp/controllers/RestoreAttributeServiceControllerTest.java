package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.RestoreAttributeReqDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.RestoreAttributeService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RestoreAttributeServiceController.class, secure = false)
public class RestoreAttributeServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private RestoreAttributeService restoreAttributeService;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void restoreHealth_Test1() throws Exception {
        //Request being made
        RestoreAttributeReqDto restoreAttributeReqDto = new RestoreAttributeReqDto("Dave");
        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(restoreAttributeReqDto);

        //Avatar retrieved from data base
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.getHealth().setRunning(50);
        avatar.setKenjaPoints(1);

        when(avatarService.getAvatarByUsername(Mockito.anyString())).thenReturn(avatar);

        //Avatar sent in response
        Avatar avatarResponse = Avatar.getStarterAvatar("Dave");
        avatarResponse.getHealth().setRunning(100);
        avatarResponse.setKenjaPoints(0);
        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(avatarResponse);

        mockMvc.perform(put("/restore-attribute-service/health")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername(any(String.class));
        verify(avatarService, times(1)).updateAvatar(any(Avatar.class));
    }

    @Test
    public void restoreHealth_Test2() throws Exception {
        //Request being made
        RestoreAttributeReqDto restoreAttributeReqDto = new RestoreAttributeReqDto("Dave");
        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(restoreAttributeReqDto);

        //Avatar retrieved from data base
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.getHealth().setRunning(50);
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername(Mockito.anyString())).thenReturn(avatar);

        //Avatar sent in response
        Avatar avatarResponse = Avatar.getStarterAvatar("Dave");
        avatarResponse.getHealth().setRunning(50);
        avatarResponse.setKenjaPoints(0);
        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(avatarResponse);

        mockMvc.perform(put("/restore-attribute-service/health")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername(any(String.class));
        verify(avatarService, times(1)).updateAvatar(any(Avatar.class));
    }

    @Test
    public void restoreHealth_Test3() throws Exception {
        //Request being made
        RestoreAttributeReqDto restoreAttributeReqDto = new RestoreAttributeReqDto("Dave");
        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(restoreAttributeReqDto);

        //Avatar retrieved from data base
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.getHealth().setRunning(100);
        avatar.setKenjaPoints(2);

        when(avatarService.getAvatarByUsername(Mockito.anyString())).thenReturn(avatar);

        //Avatar sent in response
        Avatar avatarResponse = Avatar.getStarterAvatar("Dave");
        avatarResponse.getHealth().setRunning(100);
        avatarResponse.setKenjaPoints(1);
        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(avatarResponse);

        mockMvc.perform(put("/restore-attribute-service/health")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername(any(String.class));
        verify(avatarService, times(1)).updateAvatar(any(Avatar.class));
    }

    @Test
    public void restoreManna_Test4() throws Exception {
        //Request being made
        RestoreAttributeReqDto restoreAttributeReqDto = new RestoreAttributeReqDto("Dave");
        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(restoreAttributeReqDto);

        //Avatar retrieved from data base
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.getManna().setRunning(40);
        avatar.setKenjaPoints(1);

        when(avatarService.getAvatarByUsername(Mockito.anyString())).thenReturn(avatar);

        //Avatar sent in response
        Avatar avatarResponse = Avatar.getStarterAvatar("Dave");
        avatarResponse.getManna().setRunning(60);
        avatarResponse.setKenjaPoints(0);
        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(avatarResponse);

        mockMvc.perform(put("/restore-attribute-service/manna")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername(any(String.class));
        verify(avatarService, times(1)).updateAvatar(any(Avatar.class));
    }

    @Test
    public void restoreHealth_Test5() throws Exception {
        //Request being made
        RestoreAttributeReqDto restoreAttributeReqDto = new RestoreAttributeReqDto("Dave");
        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(restoreAttributeReqDto);

        //Avatar retrieved from data base
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.getManna().setRunning(40);
        avatar.setKenjaPoints(0);

        when(avatarService.getAvatarByUsername(Mockito.anyString())).thenReturn(avatar);

        //Avatar sent in response
        Avatar avatarResponse = Avatar.getStarterAvatar("Dave");
        avatarResponse.getManna().setRunning(40);
        avatarResponse.setKenjaPoints(0);
        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(avatarResponse);

        mockMvc.perform(put("/restore-attribute-service/manna")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername(any(String.class));
        verify(avatarService, times(1)).updateAvatar(any(Avatar.class));
    }

    @Test
    public void restoreManna_Test6() throws Exception {
        //Request being made
        RestoreAttributeReqDto restoreAttributeReqDto = new RestoreAttributeReqDto("Dave");
        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(restoreAttributeReqDto);

        //Avatar retrieved from data base
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.getManna().setRunning(60);
        avatar.setKenjaPoints(2);

        when(avatarService.getAvatarByUsername(Mockito.anyString())).thenReturn(avatar);

        //Avatar sent in response
        Avatar avatarResponse = Avatar.getStarterAvatar("Dave");
        avatarResponse.getManna().setRunning(60);
        avatarResponse.setKenjaPoints(1);
        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(avatarResponse);

        mockMvc.perform(put("/restore-attribute-service/manna")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername(any(String.class));
        verify(avatarService, times(1)).updateAvatar(any(Avatar.class));
    }
}
