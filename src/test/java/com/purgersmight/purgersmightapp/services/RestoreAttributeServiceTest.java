package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.models.Avatar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RestoreAttributeServiceTest {

    @Autowired
    private RestoreAttributeService restoreAttributeService;

    @Test
    public void restoreHealth_healthLessThanMax_Test1(){
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.setKenjaPoints(2);
        avatar.getHealth().setRunning(70);
        restoreAttributeService.restoreHealth(avatar);
        assertEquals("Running health should be 100", 100, avatar.getHealth().getRunning().intValue());
        assertEquals("Kenja points should be 1", 1, avatar.getKenjaPoints());
    }

    @Test
    public void restoreHealth_notEnoughKenjaPoints_Test2(){
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.setKenjaPoints(0);
        avatar.getHealth().setRunning(70);
        restoreAttributeService.restoreHealth(avatar);
        assertEquals("Running health should be 70", 70, avatar.getHealth().getRunning().intValue());
        assertEquals("Kenja points should be 0", 0, avatar.getKenjaPoints());
    }

    @Test
    public void restoreHealth_healthAlreadyAtMax_Test3(){
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.setKenjaPoints(2);
        avatar.getHealth().setRunning(100);
        restoreAttributeService.restoreHealth(avatar);
        assertEquals("Running health should be 100", 100, avatar.getHealth().getRunning().intValue());
        assertEquals("Kenja points should be 1", 1, avatar.getKenjaPoints());
    }

    @Test
    public void restoreManna_mannaIsLessThanMax_Test4(){
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.setKenjaPoints(2);
        avatar.getManna().setRunning(30);
        restoreAttributeService.restoreManna(avatar);
        assertEquals("Running manna should be 60", 60, avatar.getManna().getRunning().intValue());
        assertEquals("Kenja points should be 1", 1, avatar.getKenjaPoints());
    }

    @Test
    public void restoreManna_notEnoughKenjaPoints_Test5(){
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.setKenjaPoints(0);
        avatar.getManna().setRunning(60);
        restoreAttributeService.restoreManna(avatar);
        assertEquals("Running manna should still be 60", 60, avatar.getManna().getRunning().intValue());
        assertEquals("Kenja points should be 0", 0, avatar.getKenjaPoints());
    }

    @Test
    public void restoreManna_mannaIsAlreadyAtMax_Test6(){
        Avatar avatar = Avatar.getStarterAvatar("Dave");
        avatar.setKenjaPoints(2);
        avatar.getManna().setRunning(60);
        restoreAttributeService.restoreManna(avatar);
        assertEquals("Running manna should still be 60", 60, avatar.getManna().getRunning().intValue());
        assertEquals("Kenja points should be 1", 1, avatar.getKenjaPoints());
    }
}
