package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateAccountControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:";

    @Before
    public void initBaseUrl() {
        baseUrl += port;
    }

    @Test
    public void createUser_shouldBeRedirectedToLoginPage_Test1() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "Angie1");
        map.add("password", "123456");
        map.add("confirmPassword", "123456");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl + "/create-account", request, String.class);

        //We get redirected back to login page
        assertTrue(response.getHeaders().get("Location").get(0).contains("/login?created"));
    }

    @Test
    public void createUser_shouldBeRedirectedBackToCreateAccountPage_Test2() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "Angie1");
        map.add("password", "123");
        map.add("confirmPassword", "123");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl + "/create-account", request, String.class);

        //We get redirected back to create-account page
        assertTrue(response.getHeaders().get("Location").get(0).contains("/create-account?error"));
    }
}