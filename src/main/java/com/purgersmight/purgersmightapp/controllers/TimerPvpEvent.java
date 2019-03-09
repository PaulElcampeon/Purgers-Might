package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.enums.AttackType;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.services.BattleService;
import com.purgersmight.purgersmightapp.services.PvpEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;


public class TimerPvpEvent implements Runnable {

    private PvpEventService pvpEventService;

    private BattleService battleService;

    private String pvpEventId;

    private String username;

    private PvpEvent pvpEvent;

    private SimpMessagingTemplate simpMessagingTemplate;

    private int moveNo;

    public TimerPvpEvent() {

    }

    @Autowired
    public TimerPvpEvent(String pvpEventId, int moveNo  ,BattleService battleService, PvpEventService pvpEventService, SimpMessagingTemplate simpMessagingTemplate) {
        this.battleService = battleService;
        this.pvpEventService = pvpEventService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.pvpEventId = pvpEventId;
        this.moveNo = moveNo;
        this.username = username;
    }

//    @Autowired
//    public TimerPvpEvent(String pvpEventId, BattleService battleService, PvpEventService pvpEventService, int moveNo) {
//        this.battleService = battleService;
//        this.pvpEventService = pvpEventService;
//        this.pvpEventId = pvpEventId;
//        this.moveNo = moveNo;
//        this.username = username;
//    }


    @Override
    public void run() {

        System.out.println("Move no is:" + moveNo);
        System.out.println(pvpEventId);
        System.out.println("STARTED THREAD");

        try {

            Thread.sleep(7000);

            if (this.checkIfMoveNoHasNotChanged()) {

//                if (this.pvpEvent.getWhosTurn().equals(this.pvpEvent.getPlayer1().getUsername())) {

                simpMessagingTemplate.convertAndSend("/topic/pvp-event/" + this.pvpEventId, this.battleService.processPlayerAttackDto(new AttackPlayerReqDto(pvpEventId, AttackType.MELEE, 0)));

//                } else {

//                }
            } else {

                System.out.println("MoveNo has Changed");
            }

            System.out.println(Thread.activeCount());

            Thread.currentThread().interrupt();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }

    private boolean checkIfMoveNoHasNotChanged() {

        System.out.println("checking if moveNo has not changed");
        System.out.println(this.pvpEventId);
        System.out.println(this.moveNo);
        this.pvpEvent = pvpEventService.getPvpEventByEventId(this.pvpEventId);
        return this.pvpEvent.getMoveNo() == this.moveNo;
    }

}
