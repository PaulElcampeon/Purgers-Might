package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.dto.CheckForInEventReqDto;
import com.purgersmight.purgersmightapp.dto.CheckForInEventResDto;
import com.purgersmight.purgersmightapp.dto.JoinPvpEventQueueReqDto;
import com.purgersmight.purgersmightapp.dto.RemoveFromPvpQueueResDto;
import com.purgersmight.purgersmightapp.enums.EventType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.repositories.PvpEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class PvpEventService {

    @Autowired
    private PvpEventRepository pvpEventRepository;

    @Autowired
    private AvatarService avatarService;

    private Logger logger = Logger.getLogger(PvpEventService.class.getName());

    private List<Avatar> avatarsInQueueForPvpEvent = new ArrayList<>();

    Lock lock = new ReentrantLock();


    public void addPvpEvent(final PvpEvent pvpEvent) {

        logger.log(Level.INFO, "New Pvp Event has just been created with id: " + pvpEvent.getEventId());

        pvpEventRepository.insert(pvpEvent);
    }

    public PvpEvent getPvpEventByEventId(final String eventId) {

        return pvpEventRepository.findByEventId(eventId).orElseThrow(NoSuchElementException::new);
    }

    public void removePvpEvent(final PvpEvent pvpEvent) {

        pvpEventRepository.delete(pvpEvent);
    }

    public void removePvpEventById(final String eventId) {

        pvpEventRepository.deleteById(eventId);
    }

    public void updatePvpEvent(final PvpEvent pvpEvent) {

        pvpEventRepository.save(pvpEvent);
    }

    public void removeAllPvpEvents() {

        pvpEventRepository.deleteAll();
    }

    public boolean existsById(final String eventId) {

        return pvpEventRepository.existsById(eventId);
    }

    public CheckForInEventResDto checkForInEvent(final CheckForInEventReqDto checkForInEventReqDto) {

        Avatar avatar = avatarService.getAvatarByUsername(checkForInEventReqDto.getUsername());

        if (avatar.isInEvent()) {

            return new CheckForInEventResDto(true, getPvpEventByEventId(avatar.getEventId()));

        } else {

            return new CheckForInEventResDto(false);
        }
    }

    public void clearPvpEventQueue() {

        avatarsInQueueForPvpEvent.clear();
    }

    public boolean checkIfAlreadyInPvpEventQueue(final JoinPvpEventQueueReqDto joinPvpEventQueueReqDto) {

        Avatar avatar = avatarService.getAvatarByUsername(joinPvpEventQueueReqDto.getUsername());

        return avatarsInQueueForPvpEvent.contains(avatar);
    }

    public RemoveFromPvpQueueResDto removeFromPvpQueue(String username) {

        lock.lock();

        Avatar tempAvatar = avatarService.getAvatarByUsername(username);

        if (checkIfInQueue(username)) {

            avatarsInQueueForPvpEvent.remove(tempAvatar);

            logger.log(Level.INFO, String.format("%s has left the pvp event queue", username));

            lock.unlock();

            return new RemoveFromPvpQueueResDto(true, null);//remove from queue

        } else {

            logger.log(Level.INFO, String.format("%s tried to leave the pvp event queue but has already been matched", username));

            lock.unlock();

            return new RemoveFromPvpQueueResDto(false, pvpEventRepository.findByEventId(tempAvatar.getEventId()).get());//get battle event
        }
    }

    public boolean checkIfInQueue(String username) {

        for (Avatar avatar : avatarsInQueueForPvpEvent) {

            if (avatar.getUsername().equals(username)) {

                return true;
            }
        }

        return false;
    }

    public void joinPvpEvent(String incomingUsername) {

        lock.lock();

        Avatar incomingAvatar = avatarService.getAvatarByUsername(incomingUsername);

        if (avatarsInQueueForPvpEvent.size() == 0) {

            avatarsInQueueForPvpEvent.add(incomingAvatar);

            logger.log(Level.INFO, incomingAvatar.getUsername() + " has joined the pvp event queue");

        } else {

            logger.log(Level.INFO, incomingAvatar.getUsername() + " has been matched in a pvp event");

            Avatar avatarInQueue = avatarsInQueueForPvpEvent.get(0);

            avatarsInQueueForPvpEvent.remove(avatarInQueue);

            createEvent(avatarInQueue, incomingAvatar);

            avatarService.updateAvatar(incomingAvatar);

            avatarService.updateAvatar(avatarInQueue);

        }

        lock.unlock();
    }

    public PvpEvent createEvent(Avatar player1, Avatar player2) {

        PvpEvent pvpEvent = new PvpEvent.PvpEventBuilder()
                .setPlayer1(player1)
                .setPlayer2(player2)
                .setEventId(player1.getUsername().concat(player2.getUsername()).concat("eventId"))
                .setEventType(EventType.PVP_EVENT)
                .setWhosTurn(player1.getUsername())
                .setEnded(false)
                .setStartTime(new Date().getTime() + 10000)
                .setTimestamp(new Date().getTime() + 20000)//should mean that the event time stamp should be 10 seconds after creation
                .setMoveNo(1)
                .build();

        player1.setEventId(pvpEvent.getEventId());

        player2.setEventId(pvpEvent.getEventId());

        player1.setInEvent(true);

        player2.setInEvent(true);

        addPvpEvent(pvpEvent);

        return pvpEvent;
    }

    public void resetPlayersPvpEventStatus(Avatar... avatars) {

        for (Avatar avatar : avatars) {

            avatar.setEventId("");

            avatar.setInEvent(false);
        }
    }
}