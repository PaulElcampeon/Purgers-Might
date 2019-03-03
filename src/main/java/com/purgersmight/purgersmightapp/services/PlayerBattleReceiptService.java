package com.purgersmight.purgersmightapp.services;

import com.mongodb.MongoWriteException;
import com.purgersmight.purgersmightapp.models.BattleReceipt;
import com.purgersmight.purgersmightapp.models.PlayerBattleReceipts;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.repositories.PlayerBattleReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Scope(value = "singleton")
public class PlayerBattleReceiptService {

    @Autowired
    private PlayerBattleReceiptRepository playerBattleReceiptRepository;

    public List<BattleReceipt> getPlayerBattleReceipts(String username) {

        PlayerBattleReceipts playerBattleReceipts = playerBattleReceiptRepository.findByUsername(username).get();

        return playerBattleReceipts.getBattleReceipts();
    }

    public void addPlayerBattleReceipt(PlayerBattleReceipts playerBattleReceipts) {

        playerBattleReceiptRepository.insert(playerBattleReceipts);
    }

    public void addBattleReceipt(String username, BattleReceipt battleReceipt) {

        PlayerBattleReceipts playerBattleReceipts;

        try {

            playerBattleReceipts = playerBattleReceiptRepository.findByUsername(username).get();

            playerBattleReceipts.getBattleReceipts().add(battleReceipt);

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

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myFormatObj.format(LocalDateTime.now());

        return new BattleReceipt(winner, loser, formattedDate);
    }

    private void createNewPlayerBattleReceipt(String username, BattleReceipt battleReceipt) {

        PlayerBattleReceipts playerBattleReceipts = new PlayerBattleReceipts(username);

        playerBattleReceipts.getBattleReceipts().add(battleReceipt);

        addPlayerBattleReceipt(playerBattleReceipts);
    }
}