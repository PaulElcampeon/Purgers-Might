package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.LoginReqDto;
import com.purgersmight.purgersmightapp.dto.LoginResDto;
import com.purgersmight.purgersmightapp.models.PlayerAvatar;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.UserService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    private String baseUrl;

    @Before
    public void initBaseUrl(){
        avatarService.removeAllAvatars();
        userService.removeAllUsers();

        baseUrl = "http://localhost:"+port;

        User newUser = new User();
        newUser.setUsername("Angie1");
        newUser.setPassword(bCryptPasswordEncoder.encode("123456"));

        userService.addUser(newUser);

        System.out.println(userService.getUserByUsername("Angie1").getRoles());

        avatarService.addAvatar(PlayerAvatar.getStarterAvatar("Angie1",null));
    }

    @Test
    public void loginController_Test1() throws JsonProcessingException {
        System.out.println(baseUrl+"/login");
        LoginReqDto loginReqDto = new LoginReqDto("Angie1","123456");
        //application/x-www-form-urlencoded
        String postBodyJson = ObjectMapperUtils.getObjectMapper().writeValueAsString(loginReqDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<String>(postBodyJson ,headers);


        System.out.println(loginReqDto);
        ResponseEntity<String> result = testRestTemplate.postForEntity(baseUrl+"/login",entity,String.class);
        System.out.println("kano");
        System.out.println(result);
    }

    @After
    public void tearDown(){
        userService.removeAllUsers();;
        avatarService.removeAllAvatars();
    }

}
