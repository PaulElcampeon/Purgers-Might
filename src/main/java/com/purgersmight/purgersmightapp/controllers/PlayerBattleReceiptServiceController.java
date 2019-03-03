package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.models.BattleReceipt;
import com.purgersmight.purgersmightapp.services.PlayerBattleReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerBattleReceiptServiceController {

    @Autowired
    private PlayerBattleReceiptService playerBattleReceiptService;

    @RequestMapping(value = "/battle-receipt-service/{username}", method = RequestMethod.GET)
    public List<BattleReceipt> getBattleReceipts(@PathVariable String username) {

        return playerBattleReceiptService.getPlayerBattleReceipts(username);
    }
}
