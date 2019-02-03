package com.purgersmight.purgersmightapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purgersmight.purgersmightapp.dto.CreateNewUserReqDto;
import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.WebSecurityTestConfig;
import com.purgersmight.purgersmightapp.controllers.UserServiceController;
import com.purgersmight.purgersmightapp.dto.CreateNewUserResDto;
import com.purgersmight.purgersmightapp.models.PlayerAvatar;
import com.purgersmight.purgersmightapp.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserServiceController.class, secure = false)
@ContextConfiguration(classes={PurgersMightAppApplication.class, WebSecurityTestConfig.class})
public class UserServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void createUser_Test1() throws Exception {
        CreateNewUserReqDto createNewUserReqDto = new CreateNewUserReqDto("Angie123","123456","123456");
        String createNewUserReqDtoAsString = objectMapper.writeValueAsString(createNewUserReqDto);

        CreateNewUserResDto createNewUserResDto = new CreateNewUserResDto(true,null, PlayerAvatar.getStarterAvatar("Angie123",null));
        String createNewUserResDtoAsString = objectMapper.writeValueAsString(createNewUserResDto);

        this.mockMvc.perform(post("/user-service/create-account")
                .with(user("admin").password("admin123").roles("USER","ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(createNewUserReqDtoAsString)
                .with(csrf())
                .accept(MediaType.ALL))
                .andExpect(status().isCreated())
                .andExpect(content().json(createNewUserResDtoAsString))
                .andReturn();

        verify(userService, times(1)).addUser(any(User.class));
    }
}