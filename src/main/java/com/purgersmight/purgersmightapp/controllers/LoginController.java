package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.LoginReqDto;
import com.purgersmight.purgersmightapp.dto.LoginResDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class LoginController {

    @Autowired
    private AvatarService avatarService;

    private Logger logger = Logger.getLogger(LoginController.class.getName());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResDto> login(@RequestBody @Valid LoginReqDto loginReqDto, BindingResult result){

        if(result.hasErrors()){
            logger.log(Level.WARNING, String.format("%s login credentials were incorrect", loginReqDto.getUsername()));
            return new ResponseEntity<>(LoginResDto.getUnsuccessfulLoginResDto(result.getAllErrors()), HttpStatus.ACCEPTED);
        }

        Avatar retrievedAvatar = avatarService.getAvatarByUsername(loginReqDto.getUsername());
        LoginResDto loginResDto = new LoginResDto(true, null, retrievedAvatar);
        logger.log(Level.WARNING, String.format("%s has logged in successfully", loginReqDto.getUsername()));
        return new ResponseEntity<>(loginResDto, HttpStatus.ACCEPTED);
    }
}
