package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.DropItemReqDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.Bag;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.utils.DropItemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DropItemUtilController {

    @Autowired
    private DropItemUtil dropItemUtil;

    @Autowired
    private AvatarService avatarService;

    @RequestMapping(value = "/drop-item", method = RequestMethod.PUT)
    public Bag dropItem(@RequestBody DropItemReqDto dropItemReqDto) {

        Avatar avatar = avatarService.getAvatarByUsername(dropItemReqDto.getUsername());

        Bag bag = dropItemUtil.dropItem(avatar.getBag(), dropItemReqDto.getIndexOfItem());

        avatar.setBag(bag);

        avatarService.updateAvatar(avatar);

        return bag;
    }
}
