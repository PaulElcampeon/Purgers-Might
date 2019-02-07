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

@RestController
public class LoginController {

    @Autowired
    private AvatarService avatarService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginReqDto){
        Avatar retrievedAvatar = avatarService.getAvatarByUsername(loginReqDto.getUsername()).get();
        LoginResDto loginResDto = new LoginResDto(true, null, retrievedAvatar);
        return new ResponseEntity<>(loginResDto, HttpStatus.ACCEPTED);
    }
}
