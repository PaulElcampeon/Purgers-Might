package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.CheckForInEventReqDto;
import com.purgersmight.purgersmightapp.dto.JoinPvpEventQueueReqDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PvpEventServiceTest {

    @Autowired
    private PvpEventService pvpEventService;

    @MockBean
    private AvatarService avatarService;

    @After
    public void tearDown(){
        pvpEventService.removeAllPvpEvents();
        pvpEventService.clearPvpEventQueue();
    }

    @Test
    public void addPvpEvent_eventShouldExist_Test1(){
        PvpEvent pvpEvent = new PvpEvent();
        pvpEvent.setEventId("Nimo");

        pvpEventService.addPvpEvent(pvpEvent);

        assertTrue(pvpEventService.existsById("Nimo"));
    }

    @Test
    public void addPvpEvent_exceptionShouldBeThrown_Test2(){
        PvpEvent pvpEvent = new PvpEvent();
        pvpEvent.setEventId("Nimo");

        pvpEventService.addPvpEvent(pvpEvent);

        assertThrows(DuplicateKeyException.class, ()->{
            pvpEventService.addPvpEvent(pvpEvent);

        });
    }

    @Test
    public void removePvpEvent_eventShouldNotExist_Test3(){
        PvpEvent pvpEvent = new PvpEvent();
        pvpEvent.setEventId("Nimo");

        pvpEventService.addPvpEvent(pvpEvent);

        assertTrue(pvpEventService.existsById("Nimo"));

        pvpEventService.removePvpEventById("Nimo");

        assertFalse(pvpEventService.existsById("Nimo"));
    }

    @Test
    public void getPvpEventByEventId_eventShouldBeReturned_Test4(){
        PvpEvent pvpEvent = new PvpEvent();
        pvpEvent.setEventId("Nimo");

        pvpEventService.addPvpEvent(pvpEvent);

        assertEquals(pvpEvent, pvpEventService.getPvpEventByEventId("Nimo"));
    }

    @Test
    public void getPvpEventByEventId_exceptionShouldBeThrown_Test5(){
        assertThrows(NoSuchElementException.class, ()->{
                pvpEventService.getPvpEventByEventId("Nimo");
        });
    }

    @Test
    public void updatePvpEventByEvent_eventShouldBeUpdated_Test6(){
        PvpEvent pvpEvent = new PvpEvent();
        pvpEvent.setEventId("Nimo");

        pvpEventService.addPvpEvent(pvpEvent);

        pvpEvent.setWhosTurn("Mike");

        pvpEventService.updatePvpEvent(pvpEvent);

        assertEquals("Mike", pvpEventService.getPvpEventByEventId("Nimo").getWhosTurn());
    }

    @Test
    public void checkForInEvent_shouldReturnTrue_Test7(){
        CheckForInEventReqDto checkForInEventReqDto = new CheckForInEventReqDto();
        checkForInEventReqDto.setUsername("Angie1");

        Avatar avatar = Avatar.getStarterAvatar("Angie1");
        avatar.setInEvent(true);
        avatar.setEventId("Miko1");

        PvpEvent pvpEvent = new PvpEvent();
        pvpEvent.setEventId("Miko1");

        pvpEventService.addPvpEvent(pvpEvent);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        assertTrue(pvpEventService.checkForInEvent(checkForInEventReqDto).isInEvent());
    }

    @Test
    public void checkForInEvent_shouldReturnFalse_Test8(){
        CheckForInEventReqDto checkForInEventReqDto = new CheckForInEventReqDto();
        checkForInEventReqDto.setUsername("Angie1");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(Avatar.getStarterAvatar("Angie1"));

        assertFalse(pvpEventService.checkForInEvent(checkForInEventReqDto).isInEvent());
    }

    @Test
    public void checkIfAlreadyInPvpEventQueue_shouldReturnTrue_Test9(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        pvpEventService.joinPvpEvent(avatar);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        JoinPvpEventQueueReqDto joinPvpEventQueueReqDto = new JoinPvpEventQueueReqDto();
        joinPvpEventQueueReqDto.setUsername("Angie1");

        assertTrue(pvpEventService.checkIfAlreadyInPvpEventQueue(joinPvpEventQueueReqDto));
    }

    @Test
    public void checkIfAlreadyInPvpEventQueue_shouldReturnFalse_Test10(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        JoinPvpEventQueueReqDto joinPvpEventQueueReqDto = new JoinPvpEventQueueReqDto();
        joinPvpEventQueueReqDto.setUsername("Angie1");

        assertFalse(pvpEventService.checkIfAlreadyInPvpEventQueue(joinPvpEventQueueReqDto));
    }

    @Test
    public void joinPvpEvent_avatarShouldBeInQueue_Test11(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        pvpEventService.joinPvpEvent(avatar);

        when(avatarService.getAvatarByUsername("Angie1")).thenReturn(avatar);

        JoinPvpEventQueueReqDto joinPvpEventQueueReqDto = new JoinPvpEventQueueReqDto();
        joinPvpEventQueueReqDto.setUsername("Angie1");

        assertTrue(pvpEventService.checkIfAlreadyInPvpEventQueue(joinPvpEventQueueReqDto));
    }

    @Test
    public void joinPvpEvent_eventShouldBeCreated_Test12(){
        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        pvpEventService.joinPvpEvent(avatar1);

        Avatar avatar2 = Avatar.getStarterAvatar("Angie2");

        pvpEventService.joinPvpEvent(avatar2);

        assertTrue(pvpEventService.existsById(avatar1.getUsername().concat(avatar2.getUsername().concat("eventId"))));
    }

    @Test
    public void createEvent_eventShouldBeCreated_Test13(){
        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");
        Avatar avatar2 = Avatar.getStarterAvatar("Angie2");

        pvpEventService.createEvent(avatar1, avatar2);

        assertTrue(pvpEventService.existsById(avatar1.getUsername().concat(avatar2.getUsername().concat("eventId"))));
    }
}
