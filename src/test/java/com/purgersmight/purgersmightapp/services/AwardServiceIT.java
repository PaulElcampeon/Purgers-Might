package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AwardServiceIT {

    @Autowired
    private AwardService awardService;

    @Test
    public void awardKenjaPoints_shouldRecieve2KenjaPoint_Test1(){

        Avatar avatar = Avatar.getStarterAvatar("Danny");

        avatar.setKenjaPoints(0);

        awardService.awardKenjaPoints(avatar);

        assertEquals(2, avatar.getKenjaPoints());
    }

    @Test
    public void awardExperienceAndLevelAndKenjaPoints__Test2(){

        Avatar avatar1 = Avatar.getStarterAvatar("Danny");

        Avatar avatar2 = Avatar.getStarterAvatar("Richie");

        awardService.awardExperienceAndLevelAndKenjaPoints(avatar1, avatar2);

        //Winner
        assertEquals(20, avatar1.getExperience().getRunning().intValue());

        assertEquals(2, avatar1.getKenjaPoints());

        assertEquals(1, avatar1.getLevel());

        //Loser
        assertEquals(0, avatar2.getExperience().getRunning().intValue());

        assertEquals(0, avatar2.getKenjaPoints());

        assertEquals(1, avatar2.getLevel());
    }

    @Test
    public void awardExperienceAndLevelAndKenjaPoints__Test3(){

        Avatar avatar1 = Avatar.getStarterAvatar("Danny");

        avatar1.getExperience().setRunning(90);

        Avatar avatar2 = Avatar.getStarterAvatar("Richie");

        awardService.awardExperienceAndLevelAndKenjaPoints(avatar1, avatar2);

        //Winner
        assertEquals(10, avatar1.getExperience().getRunning().intValue());

        assertEquals(110, avatar1.getExperience().getActual().intValue());

        assertEquals(2, avatar1.getKenjaPoints());

        assertEquals(2, avatar1.getLevel());

        //Loser
        assertEquals(0, avatar2.getExperience().getRunning().intValue());

        assertEquals(0, avatar2.getKenjaPoints());

        assertEquals(1, avatar2.getLevel());
    }

    @Test
    public void awardWinningPlayer__Test4(){

        Avatar avatar1 = Avatar.getStarterAvatar("Danny");

        Avatar avatar2 = Avatar.getStarterAvatar("Richie");

        avatar2.getHealth().setRunning(0);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(avatar1);

        pvpEvent.setPlayer2(avatar2);

        awardService.awardExperienceAndLevelAndKenjaPoints(avatar1, avatar2);

        //Winner
        assertEquals(20, avatar1.getExperience().getRunning().intValue());

        assertEquals(2, avatar1.getKenjaPoints());

        assertEquals(1, avatar1.getLevel());

        //Loser
        assertEquals(0, avatar2.getExperience().getRunning().intValue());

        assertEquals(0, avatar2.getKenjaPoints());

        assertEquals(1, avatar2.getLevel());
    }

    @Test
    public void awardWinningPlayer__Test5(){

        Avatar avatar1 = Avatar.getStarterAvatar("Danny");

        avatar1.getExperience().setRunning(90);

        Avatar avatar2 = Avatar.getStarterAvatar("Richie");

        avatar2.getHealth().setRunning(0);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(avatar1);

        pvpEvent.setPlayer2(avatar2);

        awardService.awardExperienceAndLevelAndKenjaPoints(avatar1, avatar2);

        //Winner
        assertEquals(10, avatar1.getExperience().getRunning().intValue());

        assertEquals(110, avatar1.getExperience().getActual().intValue());

        assertEquals(2, avatar1.getKenjaPoints());

        assertEquals(2, avatar1.getLevel());

        //Loser
        assertEquals(0, avatar2.getExperience().getRunning().intValue());

        assertEquals(0, avatar2.getKenjaPoints());

        assertEquals(1, avatar2.getLevel());
    }
}

