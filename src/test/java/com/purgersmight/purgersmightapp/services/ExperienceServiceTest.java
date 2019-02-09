package com.purgersmight.purgersmightapp.services;

import static org.junit.Assert.*;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class ExperienceServiceTest {

    //experience.accumulator is set to 4 in the application.properties file in test path

    @Autowired
    private ExperienceService experienceService;

    @Test
    public void getExperience_Test1(){
        int expectedExperiencePoints = experienceService.getExperience(5,5);
        assertEquals("Result should be equal to 20",expectedExperiencePoints, 20);
    }

    @Test
    public void getExperience_Test2(){
        int expectedExperiencePoints = experienceService.getExperience(4,5);
        assertEquals("Result should be equal to 24",expectedExperiencePoints, 24);
    }

    @Test
    public void getExperience_Test3(){
        int expectedExperiencePoints = experienceService.getExperience(6,5);
        assertEquals("Result should be equal to 16",expectedExperiencePoints, 16);
    }

    @Test
    public void getExperience_Test4(){
        int expectedExperiencePoints = experienceService.getExperience(10,5);
        assertEquals("Result should be equal to 0",expectedExperiencePoints, 0);
    }

    @Test
    public void getExperience_Test5(){
        int expectedExperiencePoints = experienceService.getExperience(9,5);
        assertEquals("Result should be equal to 4",expectedExperiencePoints, 4);
    }

    @Test
    public void getExperience_Test6(){
        int expectedExperiencePoints = experienceService.getExperience(5,9);
        assertEquals("Result should be equal to 36",expectedExperiencePoints, 36);
    }

    @Test
    public void getExperience_Test7(){
        int expectedExperiencePoints = experienceService.getExperience(5,11);
        assertEquals("Result should be equal to 40",expectedExperiencePoints, 40);
    }
}
