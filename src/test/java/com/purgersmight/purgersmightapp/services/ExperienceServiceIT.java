package com.purgersmight.purgersmightapp.services;

import static org.junit.Assert.*;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExperienceServiceIT {

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
    public void getPlayerExperience_Test1(){
        ResponseEntity<String> respEntity = testRestTemplate.getForEntity(baseUrl+"/experience-service/get-experience/5/5", String.class);
        assertEquals(respEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(respEntity.getBody(), "20");
    }

    @Test
    public void getPlayerExperience_Test2(){
        ResponseEntity<String> respEntity = testRestTemplate.getForEntity(baseUrl+"/experience-service/get-experience/5/6", String.class);
        assertEquals(respEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(respEntity.getBody(), "24");
    }

    @Test
    public void getPlayerExperience_Test3(){
        ResponseEntity<String> respEntity = testRestTemplate.getForEntity(baseUrl+"/experience-service/get-experience/6/5", String.class);
        assertEquals(respEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(respEntity.getBody(), "16");
    }

    @Test
    public void getPlayerExperience_Test4(){
        ResponseEntity<String> respEntity = testRestTemplate.getForEntity(baseUrl+"/experience-service/get-experience/10/5", String.class);
        assertEquals(respEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(respEntity.getBody(), "0");
    }
}
