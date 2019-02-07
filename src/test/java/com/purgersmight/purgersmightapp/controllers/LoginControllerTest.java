package com.purgersmight.purgersmightapp.controllers;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.CustomUserDetails;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.CustomUserDetailsService;
import com.purgersmight.purgersmightapp.services.ExperienceService;
import com.purgersmight.purgersmightapp.services.UserService;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ExperienceServiceController.class, secure = false)
@ContextConfiguration(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class})
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private UserService userService;

    @MockBean
    private AvatarService avatarService;


    @Test
    public void loginController_Test1() throws Exception {

        User newUser = new User("Angie1", "123456");

        when(userService.getUserByUsername("Angie1")).thenReturn(newUser);

        when(userDetailsService.loadUserByUsername("Angie1")).thenReturn(new CustomUserDetails(newUser));

        MvcResult result = mockMvc.perform(post("/login")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("username", "Angie1")
                .param("password", "123456"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getCookies().toString());
        System.out.println(result.getResponse().getContentType());


    }

    @After
    public void tearDown(){
        userService.removeAllUsers();;
        avatarService.removeAllAvatars();
    }

}
