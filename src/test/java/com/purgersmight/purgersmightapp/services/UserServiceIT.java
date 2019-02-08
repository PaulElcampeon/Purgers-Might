package com.purgersmight.purgersmightapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.CreateNewUserReqDto;
import com.purgersmight.purgersmightapp.dto.CreateNewUserResDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:";

    @Before
    public void initBaseUrl(){
        baseUrl+= port;
    }

    @Test
    public void createUser_Test() throws JsonProcessingException {
        CreateNewUserReqDto  createNewUserReqDto = new CreateNewUserReqDto("Angie1","123465","123465");

        ResponseEntity<String> result = testRestTemplate.postForEntity("/user-service/create-account", createNewUserReqDto, String.class);

        CreateNewUserResDto createNewUserResDto = new CreateNewUserResDto(true,null, Avatar.getStarterAvatar("Angie1",null));
        String createNewUserResDtoAsString = ObjectMapperUtils.getObjectMapper().writeValueAsString(createNewUserResDto);

        assertEquals(createNewUserResDtoAsString, result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        System.out.println(result.getHeaders());
    }
}
