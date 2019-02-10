package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purgersmight.purgersmightapp.services.UserService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UserServiceController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(UserServiceController.class.getName());

    @RequestMapping(value = "/user-service/{username}", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@PathVariable String username) throws JsonProcessingException {
        String retrievedUserAsString = ObjectMapperUtils.getObjectMapper().writeValueAsString(userService.getUserByUsername(username));
        return new ResponseEntity<>(retrievedUserAsString, HttpStatus.OK);
    }

}
