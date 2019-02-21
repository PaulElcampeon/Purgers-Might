package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.*;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.repositories.PvpEventRepository;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.PvpEventService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PvpEventServiceController.class, secure = false)
public class PvpEventServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private PvpEventService pvpEventService;

    @MockBean
    private PvpEventRepository pvpEventRepository;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void joinEventQueue_shouldCall_Test1() throws Exception {
        JoinPvpEventQueueReqDto joinPvpEventQueueReqDto = new JoinPvpEventQueueReqDto();

        joinPvpEventQueueReqDto.setUsername("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(joinPvpEventQueueReqDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        pvpEventService.joinPvpEvent("Angie1");//avatarService.getAvatarByUsername("Angie1") called once here

        mockMvc.perform(post("/pvp-event-service/join-event-queue")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq));

        verify(avatarService, times(2)).getAvatarByUsername("Angie1");

        verify(pvpEventService, times(1)).checkIfAlreadyInPvpEventQueue(joinPvpEventQueueReqDto);

        verify(pvpEventService, times(1)).joinPvpEvent("Angie1");

        assertTrue(pvpEventService.checkIfInQueue("Angie1"));

        pvpEventService.removeFromPvpQueue("Angie1");
    }

    @Test
    public void joinEventQueue_shouldBeAdded_Test2() throws Exception {
        JoinPvpEventQueueReqDto joinPvpEventQueueReqDto = new JoinPvpEventQueueReqDto();

        joinPvpEventQueueReqDto.setUsername("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(joinPvpEventQueueReqDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        mockMvc.perform(post("/pvp-event-service/join-event-queue")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq));

        verify(avatarService, times(2)).getAvatarByUsername("Angie1");

        assertTrue(pvpEventService.checkIfInQueue("Angie1"));

        pvpEventService.removeFromPvpQueue("Angie1");
    }

    @Test
    public void joinEventQueue_shouldNotBeAdded_Test3() throws Exception {
        JoinPvpEventQueueReqDto joinPvpEventQueueReqDto = new JoinPvpEventQueueReqDto();

        joinPvpEventQueueReqDto.setUsername("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(joinPvpEventQueueReqDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        //Need to use stubs when mocking SpyBeans
        doReturn(true).when(pvpEventService).checkIfAlreadyInPvpEventQueue(joinPvpEventQueueReqDto);

        mockMvc.perform(post("/pvp-event-service/join-event-queue")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq));

        assertFalse(pvpEventService.checkIfInQueue("Angie1"));
    }

    @Test
    public void checkForInEvent_returnTrue_Test4() throws Exception {
        CheckForInEventReqDto checkForInEventReqDto = new CheckForInEventReqDto("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(checkForInEventReqDto);

        CheckForInEventResDto checkForInEventResDto = new CheckForInEventResDto(false, null);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(checkForInEventResDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        avatar1.setInEvent(false);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        mockMvc.perform(post("/pvp-event-service/check-for-in-event")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie1");
    }

    @Test
    public void checkForInEvent_returnFalse_Test5() throws Exception {
        CheckForInEventReqDto checkForInEventReqDto = new CheckForInEventReqDto("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(checkForInEventReqDto);

        PvpEvent pvpEvent = new PvpEvent.PvpEventBuilder().setEventId("meg").build();

        CheckForInEventResDto checkForInEventResDto = new CheckForInEventResDto(true, pvpEvent);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(checkForInEventResDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        avatar1.setInEvent(true);

        avatar1.setEventId("aadwaw");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        doReturn(pvpEvent).when(pvpEventService).getPvpEventByEventId(anyString());

        mockMvc.perform(post("/pvp-event-service/check-for-in-event")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie1");
    }

    @Test
    public void removeFromQueue_returnTrue_Test6() throws Exception {
        RemoveFromPvpQueueReqDto removeFromPvpQueueReqDto = new RemoveFromPvpQueueReqDto("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(removeFromPvpQueueReqDto);

        RemoveFromPvpQueueResDto removeFromPvpQueueResDto = new RemoveFromPvpQueueResDto(true, null);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(removeFromPvpQueueResDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        pvpEventService.joinPvpEvent("Angie1");

        mockMvc.perform(post("/pvp-event-service/remove-from-queue")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(2)).getAvatarByUsername("Angie1");
    }

    @Test
    public void removeFromQueue_returnFalse_Test7() throws Exception {
        RemoveFromPvpQueueReqDto removeFromPvpQueueReqDto = new RemoveFromPvpQueueReqDto("Angie1");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(removeFromPvpQueueReqDto);

        PvpEvent pvpEvent = new PvpEvent.PvpEventBuilder().setEventId("meg").build();

        RemoveFromPvpQueueResDto removeFromPvpQueueResDto = new RemoveFromPvpQueueResDto(false, pvpEvent);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(removeFromPvpQueueResDto);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        avatar1.setEventId("meg");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar1);

        when(pvpEventRepository.findByEventId(anyString())).thenReturn(Optional.of(pvpEvent));

        mockMvc.perform(post("/pvp-event-service/remove-from-queue")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(1)).getAvatarByUsername("Angie1");

        verify(pvpEventRepository, times(1)).findByEventId("meg");
    }
}
