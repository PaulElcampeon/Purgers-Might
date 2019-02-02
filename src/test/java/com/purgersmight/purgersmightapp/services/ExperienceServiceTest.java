package com.purgersmight.purgersmightapp.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExperienceServiceTest {

    @Test
    public void getExperience_Test1(){
        int result = ExperienceService.getExperience(5,5);
        assertEquals("Result should be equal to 20",result, 20);
    }
}
