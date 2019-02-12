package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.UserService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserServiceController.class, secure = false)
public class UserServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void getUser_userShouldBeReturned_Test1() throws Exception {

        Optional<User> userOptional = Optional.of(User.getTesterUser());

        when(userService.getUserByUsername(Mockito.anyString())).thenReturn(userOptional);

        MvcResult result = this.mockMvc.perform(get("/user-service/angie")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(ObjectMapperUtils.getObjectMapper().writeValueAsString(userOptional.get()), resultContent);
    }

    @Test
    public void getUser_badRequestHTTPStatus_Test2() throws Exception {

        Optional<User> userOptional = Optional.empty();

        when(userService.getUserByUsername(Mockito.anyString())).thenReturn(userOptional);

        MvcResult result = this.mockMvc.perform(get("/user-service/angie")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(userOptional.toString(), resultContent);
    }
}
