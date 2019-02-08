package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.services.CustomUserDetailsService;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.services.ExperienceService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ExperienceServiceController.class, secure = false)
@ContextConfiguration(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class})
public class ExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserService userService;

    @Test
    public void getExperiencePoint_Test1() throws Exception {

        mockMvc.perform(get("/experience-service/get-experience/7/8")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(experienceService, times(1)).getExperience(7,8);
    }
}
