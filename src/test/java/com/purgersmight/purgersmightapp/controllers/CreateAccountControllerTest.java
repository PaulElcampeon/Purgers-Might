package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.BattleStatisticsService;
import com.purgersmight.purgersmightapp.services.PlayerBattleReceiptService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CreateAccountController.class)
public class CreateAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private UserService userService;

    @MockBean
    private PlayerBattleReceiptService playerBattleReceiptService;

    @MockBean
    private BattleStatisticsService battleStatisticsService;

    @Test
    public void createUser_shouldBeRedirectedToLoginPage_Test1() throws Exception {
        mockMvc.perform(post("/create-account")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "Angie1")
                .param("password", "123456")
                .param("confirmPassword", "123456"))
                .andExpect(redirectedUrl("/login?created"))
                .andReturn();
    }

    @Test
    public void createUser_shouldBeRedirectedBackToCreateAccountPage_Test2() throws Exception {
        mockMvc.perform(post("/create-account")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "Angie1")
                .param("password", "1234")
                .param("confirmPassword", "1234"))
                .andExpect(redirectedUrl("/create-account?error"))
                .andReturn();
    }
}