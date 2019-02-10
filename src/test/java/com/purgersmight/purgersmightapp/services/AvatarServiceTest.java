package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.Avatar;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AvatarServiceTest {

    @Autowired
    private AvatarService avatarService;

    @After
    public void tearDown(){
        avatarService.removeAllAvatars();
    }

    @Test
    public void addAvatar_userShouldExistInDB_Test1(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar);

        assertTrue(avatarService.existsById("Angie1"));
    }

    @Test
    public void addAvatar_exceptionShouldBeThrown_Test2(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar);

        assertThrows(DuplicateKeyException.class,()->{
            avatarService.addAvatar(avatar);
        });
    }

    @Test
    public void getAvatarByUsername_avatarShouldBeReturned_Test3(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar);

        assertEquals(avatar,avatarService.getAvatarByUsername("Angie1"));
    }

    @Test
    public void getAvatarByUsername_exceptionShouldBeThrown_Test4(){
        assertThrows(NoSuchElementException.class,()->{
            avatarService.getAvatarByUsername("Angie1");
        });
    }

    @Test
    public void removeAvatar_avatarShouldNoLongerExist_Test5(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar);

        avatarService.removeAvatar(avatar);

        assertFalse(avatarService.existsById("Angie1"));
    }

    @Test
    public void removeAvatarById_avatarShouldNoLongerExist_Test6(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar);

        avatarService.removeAvatarById("Angie1");

        assertFalse(avatarService.existsById("Angie1"));
    }

    @Test
    public void updateAvatar_avatarShouldBeUpdated_Test7(){
        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar);

        avatar.setEventId("pico1");

        avatarService.updateAvatar(avatar);

        assertEquals("pico1", avatarService.getAvatarByUsername("Angie1").getEventId());
    }

}
