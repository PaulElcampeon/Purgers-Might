package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.Avatar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LevelServiceTest {

    @Autowired
    private LevelService levelService;

    @Test
    public void levelUpAvatar_shouldBeLevel2ExperienceShouldBe0_Test1() {

        Avatar avatar = Avatar.getStarterAvatar("Dave");

        avatar.setLevel(1);

        avatar.getExperience().setRunning(100);

        levelService.levelUpAvatar(avatar);

        assertEquals(2, avatar.getLevel());

        assertEquals(0, avatar.getExperience().getRunning().intValue());

        assertEquals(110, avatar.getExperience().getActual().intValue());
    }

    @Test
    public void levelUpAvatar_shouldBeLevel2ExperienceShouldBe100_Test2() {

        Avatar avatar = Avatar.getStarterAvatar("Dave");

        avatar.setLevel(1);

        avatar.getExperience().setRunning(200);

        levelService.levelUpAvatar(avatar);

        assertEquals(2, avatar.getLevel());

        assertEquals(100, avatar.getExperience().getRunning().intValue());

        assertEquals(110, avatar.getExperience().getActual().intValue());
    }

    @Test
    public void levelUpAvatar_shouldBeLevel3ExperienceShouldBe19_Test3() {

        Avatar avatar = Avatar.getStarterAvatar("Dave");

        avatar.setLevel(1);

        avatar.getExperience().setRunning(350);

        levelService.levelUpAvatar(avatar);

        assertEquals(4, avatar.getLevel());

        assertEquals(19, avatar.getExperience().getRunning().intValue());

        assertEquals(133, avatar.getExperience().getActual().intValue());
    }

    @Test
    public void levelUpAvatar_shouldBeLevel1ExperienceShouldBe90_Test4() {

        Avatar avatar = Avatar.getStarterAvatar("Dave");

        avatar.setLevel(1);

        avatar.getExperience().setRunning(90);

        levelService.levelUpAvatar(avatar);

        assertEquals(1, avatar.getLevel());

        assertEquals(90, avatar.getExperience().getRunning().intValue());

        assertEquals(100, avatar.getExperience().getActual().intValue());
    }

    @Test
    public void levelAvatarAttributes_health105Manna63Exp110_Test5() {

        Avatar avatar = Avatar.getStarterAvatar("Dave");

        levelService.levelAvatarAttributes(avatar);

        assertEquals(105, avatar.getHealth().getActual().intValue());

        assertEquals(63, avatar.getManna().getActual().intValue());

        assertEquals(110, avatar.getExperience().getActual().intValue());
    }

    @Test
    public void levelAvatarAttributes_health110Manna66Exp121_Test6() {

        Avatar avatar = Avatar.getStarterAvatar("Dave");

        avatar.getExperience().setActual(110);

        avatar.getHealth().setActual(105);

        avatar.getManna().setActual(63);

        levelService.levelAvatarAttributes(avatar);

        assertEquals(110, avatar.getHealth().getActual().intValue());

        assertEquals(66, avatar.getManna().getActual().intValue());

        assertEquals(121, avatar.getExperience().getActual().intValue());
    }
}
