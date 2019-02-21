package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.AttackPlayerResDto;
import com.purgersmight.purgersmightapp.services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BattleServiceController {

    @Autowired
    private BattleService battleService;

    @MessageMapping(value = "/pvp-event/{eventId}")
    @SendTo(value = "/topic/pvp-event/{eventId}")
    public AttackPlayerResDto attackPlayer(@DestinationVariable String eventId, @RequestBody AttackPlayerReqDto attackPlayerReqDto) {

        return battleService.processPlayerAttackDto(attackPlayerReqDto);
    }
}
