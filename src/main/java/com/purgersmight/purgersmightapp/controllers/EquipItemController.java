package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.EquipItemReqDto;
import com.purgersmight.purgersmightapp.dto.EquipItemResDto;
import com.purgersmight.purgersmightapp.utils.EquipItemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquipItemController {

    @Autowired
    private EquipItemUtil equipItemUtil;

    @RequestMapping(value = "/equip-item", method = RequestMethod.PUT)
    public EquipItemResDto equipItem(@RequestBody EquipItemReqDto equipItemReqDto) {

        return equipItemUtil.equipItem(equipItemReqDto);
    }

}
