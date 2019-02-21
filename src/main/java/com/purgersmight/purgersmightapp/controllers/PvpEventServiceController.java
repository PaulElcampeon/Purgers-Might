package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.*;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.services.PvpEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
public class PvpEventServiceController {

    @Autowired
    private PvpEventService pvpEventService;

    @RequestMapping(value = "/pvp-event-service/join-event-queue", method = RequestMethod.POST)
    public void joinPvpQueue(@RequestBody final JoinPvpEventQueueReqDto joinPvpEventQueueReqDto) {

        if (!pvpEventService.checkIfAlreadyInPvpEventQueue(joinPvpEventQueueReqDto)) {

            pvpEventService.joinPvpEvent(joinPvpEventQueueReqDto.getUsername());
        }
    }

    @RequestMapping(value = "/pvp-event-service/check-for-in-event", method = RequestMethod.POST)
    public ResponseEntity<CheckForInEventResDto> checkForInEvent(@RequestBody final CheckForInEventReqDto checkForInEventReqDto) {

        return new ResponseEntity<>(pvpEventService.checkForInEvent(checkForInEventReqDto), HttpStatus.OK);
    }

    @RequestMapping(value = "/pvp-event-service/remove-from-queue", method = RequestMethod.POST)
    public ResponseEntity<RemoveFromPvpQueueResDto> removeFromPvpQueue(@RequestBody final RemoveFromPvpQueueReqDto removeFromPvpQueueReqDto) {

        return new ResponseEntity<>(pvpEventService.removeFromPvpQueue(removeFromPvpQueueReqDto.getUsername()), HttpStatus.OK);
    }

    @RequestMapping(value = "/pvp-event-service/{eventId}", method = RequestMethod.GET)
    public GetPvpEventResDto getPvpEvent(@PathVariable String eventId, HttpServletRequest request) {

        if(pvpEventService.existsById(eventId)){

            return new GetPvpEventResDto(true, pvpEventService.getPvpEventByEventId(eventId));
        }

        return new GetPvpEventResDto(true, null);
    }
}
