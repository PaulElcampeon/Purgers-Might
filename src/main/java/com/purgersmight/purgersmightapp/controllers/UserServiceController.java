package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purgersmight.purgersmightapp.dto.CreateNewUserReqDto;
import com.purgersmight.purgersmightapp.dto.CreateNewUserResDto;
import com.purgersmight.purgersmightapp.models.PlayerAvatar;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.UserService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
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
public class UserServiceController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(UserServiceController.class.getName());

    @RequestMapping(value = "/user-service/create-account", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateNewUserReqDto createNewUserReqDto, BindingResult result) throws JsonProcessingException {

        if(result.hasErrors()){
            logger.log(Level.INFO, String.format("%s has tried to create an account but was unsuccessful",createNewUserReqDto.getUsername()));
            CreateNewUserResDto createNewUserResDtoError = new CreateNewUserResDto(false,result.getAllErrors(),null);
            String createNewUserResDtoErrorAsString = ObjectMapperUtils.getObjectMapper().writeValueAsString(createNewUserResDtoError);
            return new ResponseEntity<>(
                    createNewUserResDtoErrorAsString,
                    HttpStatus.BAD_REQUEST
            );
        }

        User newUser = new User(createNewUserReqDto.getUsername(), createNewUserReqDto.getPassword());
        CreateNewUserResDto createNewUserResDto = new CreateNewUserResDto(true,null,PlayerAvatar.getStarterAvatar(createNewUserReqDto.getUsername(),null));
        String createNewUserResDtoAsString = ObjectMapperUtils.getObjectMapper().writeValueAsString(createNewUserResDto);

        userService.addUser(newUser);

        return new ResponseEntity<>(
                createNewUserResDtoAsString,
                HttpStatus.CREATED
        );
    }
}
