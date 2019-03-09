package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.AttackPlayerResDto;
import com.purgersmight.purgersmightapp.dto.ForfeitPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.ForfeitPlayerResDto;
import com.purgersmight.purgersmightapp.services.BattleService;
import com.purgersmight.purgersmightapp.services.PvpEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BattleServiceController {

    @Autowired
    private BattleService battleService;

    @Autowired
    private PvpEventService pvpEventService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(value = "/pvp-event/{eventId}")
    @SendTo(value = "/topic/pvp-event/{eventId}")
    public AttackPlayerResDto attackPlayer(@DestinationVariable String eventId, @RequestBody AttackPlayerReqDto attackPlayerReqDto) {

        battleService.setTemplate(simpMessagingTemplate);
        AttackPlayerResDto attackPlayerResDto = battleService.processPlayerAttackDto(attackPlayerReqDto);
        return attackPlayerResDto;
    }

    @MessageMapping(value = "/pvp-event/forfeit/{eventId}")
    @SendTo(value = "/topic/pvp-event/{eventId}")
    public ForfeitPlayerResDto forfeitPlayer(@DestinationVariable String eventId, @RequestBody ForfeitPlayerReqDto forfeitPlayerReqDto) {

        return battleService.processForfeitReq(forfeitPlayerReqDto);
    }

}
