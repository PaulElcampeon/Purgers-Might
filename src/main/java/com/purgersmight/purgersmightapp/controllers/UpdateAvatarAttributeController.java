package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeReqDto;
import com.purgersmight.purgersmightapp.dto.UpdateAvatarAttributeResDto;
import com.purgersmight.purgersmightapp.utils.UpdateAvatarAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateAvatarAttributeController {

    @Autowired
    private UpdateAvatarAttribute updateAvatarAttribute;

    @RequestMapping(value = "/update-avatar-attribute", method = RequestMethod.PUT)
    public UpdateAvatarAttributeResDto updateAvatarAttribute(@RequestBody UpdateAvatarAttributeReqDto updateAvatarAttributeReqDto) {

        return updateAvatarAttribute.updateAttributes(updateAvatarAttributeReqDto);
    }

}
