package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.ChangeSpellReqDto;
import com.purgersmight.purgersmightapp.dto.ChangeSpellResDto;
import com.purgersmight.purgersmightapp.utils.ChangeSpellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeSpellController {

    @Autowired
    private ChangeSpellUtil changeSpellUtil;

    @RequestMapping(value = "/change-spell", method = RequestMethod.PUT)
    public ChangeSpellResDto changeSpell(@RequestBody ChangeSpellReqDto changeSpellReqDto) {

        return changeSpellUtil.changeSpell(changeSpellReqDto);
    }

}
