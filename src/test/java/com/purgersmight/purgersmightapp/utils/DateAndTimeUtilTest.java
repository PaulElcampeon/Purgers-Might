package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DateAndTimeUtilTest {

    @Autowired
    private DateAndTimeUtil dateAndTimeUtil;

    @Test
    public void getDate_Test1() {

        assertNotNull(dateAndTimeUtil.getDate());
    }

    @Test
    public void getTime_Test2() {

        assertNotNull(dateAndTimeUtil.getTime());
    }
}
