package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.CustomUserDetailsService;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.CreateNewUserReqDto;
import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.dto.CreateNewUserResDto;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.UserService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserServiceController.class, secure = false)
@ContextConfiguration(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class})
public class UserServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void createUser_Test1() throws Exception {
        CreateNewUserReqDto createNewUserReqDto = new CreateNewUserReqDto("Angie123", "123456", "123456");
        String createNewUserReqDtoAsString = objectMapper.writeValueAsString(createNewUserReqDto);

        CreateNewUserResDto createNewUserResDto = new CreateNewUserResDto(true, null, Avatar.getStarterAvatar("Angie123"));
        String createNewUserResDtoAsString = objectMapper.writeValueAsString(createNewUserResDto);

        this.mockMvc.perform(post("/user-service/create-account")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(createNewUserReqDtoAsString)
                .accept(MediaType.ALL))
                .andExpect(status().isCreated())
                .andExpect(content().json(createNewUserResDtoAsString))
                .andReturn();

        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    public void getUser_Test2() throws Exception {

        when(userService.getUserByUsername(Mockito.anyString())).thenReturn(User.getTesterUser());

        MvcResult result = this.mockMvc.perform(get("/user-service/angie")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        assertEquals(ObjectMapperUtils.getObjectMapper().writeValueAsString(User.getTesterUser()), resultContent);
    }

}
