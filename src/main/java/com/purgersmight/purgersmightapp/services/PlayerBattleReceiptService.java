package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.BattleReceipt;
import com.purgersmight.purgersmightapp.models.PlayerBattleReceipts;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.repositories.PlayerBattleReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class PlayerBattleReceiptService {

    @Autowired
    private PlayerBattleReceiptRepository playerBattleReceiptRepository;

    private Logger logger = Logger.getLogger(PlayerBattleReceiptService.class.getName());

    public List<BattleReceipt> getPlayerBattleReceipts(String username) {

        try {

            PlayerBattleReceipts playerBattleReceipts = playerBattleReceiptRepository.findByUsername(username).get();

            logger.log(Level.INFO, String.format("%s has requested their battle receipts", username));

            return playerBattleReceipts.getBattleReceipts();

        } catch (NoSuchElementException e) {

            return Collections.EMPTY_LIST;

        }
    }

    public void addPlayerBattleReceipt(PlayerBattleReceipts playerBattleReceipts) {

        playerBattleReceiptRepository.insert(playerBattleReceipts);
    }

    public void addBattleReceipt(String username, BattleReceipt battleReceipt) {

        PlayerBattleReceipts playerBattleReceipts;

        try {

            playerBattleReceipts = playerBattleReceiptRepository.findByUsername(username).get();

            playerBattleReceipts.getBattleReceipts().add(battleReceipt);

            playerBattleReceiptRepository.save(playerBattleReceipts);

        } catch (NoSuchElementException e) {

            createNewPlayerBattleReceipt(username, battleReceipt);
        }
    }

    public BattleReceipt createBattleReceipt(PvpEvent pvpEvent) {

        BattleReceipt battleReceipt;

        if (pvpEvent.getPlayer1().getHealth().getRunning() == 0) {

            battleReceipt = getReceipt(pvpEvent.getPlayer2().getUsername(), pvpEvent.getPlayer1().getUsername());

            addBattleReceipt(pvpEvent.getPlayer1().getUsername(), battleReceipt);

            addBattleReceipt(pvpEvent.getPlayer2().getUsername(), battleReceipt);

        } else {

            battleReceipt = getReceipt(pvpEvent.getPlayer1().getUsername(), pvpEvent.getPlayer2().getUsername());

            addBattleReceipt(pvpEvent.getPlayer1().getUsername(), battleReceipt);

            addBattleReceipt(pvpEvent.getPlayer2().getUsername(), battleReceipt);
        }

        return battleReceipt;
    }

    private BattleReceipt getReceipt(String winner, String loser) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formattedDate = dateFormatter.format(LocalDate.now());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedTime = timeFormatter.format(LocalTime.now());

        return new BattleReceipt(winner, loser, formattedDate, formattedTime);
    }

    private void createNewPlayerBattleReceipt(String username, BattleReceipt battleReceipt) {

        PlayerBattleReceipts playerBattleReceipts = new PlayerBattleReceipts(username);

        playerBattleReceipts.getBattleReceipts().add(battleReceipt);

        addPlayerBattleReceipt(playerBattleReceipts);
    }
}
