package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.RestoreAttributeReqDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.RestoreAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestoreAttributeServiceController {

    @Autowired
    private RestoreAttributeService restoreAttributeService;

    @Autowired
    private AvatarService avatarService;

    @RequestMapping(value = "/restore-attribute-service/health", method = RequestMethod.PUT)
    public ResponseEntity<Avatar> restoreHealth(@RequestBody RestoreAttributeReqDto restoreAttributeReqDto) {
        Avatar playerAvatar = avatarService.getAvatarByUsername(restoreAttributeReqDto.getUsername());

        restoreAttributeService.restoreHealth(playerAvatar);
        avatarService.updateAvatar(playerAvatar);

        return new ResponseEntity<>(playerAvatar, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/restore-attribute-service/manna", method = RequestMethod.PUT)
    public ResponseEntity<Avatar> restoreManna(@RequestBody RestoreAttributeReqDto restoreAttributeReqDto) {
        Avatar playerAvatar = avatarService.getAvatarByUsername(restoreAttributeReqDto.getUsername());

        restoreAttributeService.restoreManna(playerAvatar);
        avatarService.updateAvatar(playerAvatar);

        return new ResponseEntity<>(playerAvatar, HttpStatus.ACCEPTED);
    }
}
