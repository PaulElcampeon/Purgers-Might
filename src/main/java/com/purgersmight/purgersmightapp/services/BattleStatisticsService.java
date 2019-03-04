package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.BattleStatistics;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.repositories.BattleStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(value = "singleton")
public class BattleStatisticsService {

    @Autowired
    private BattleStatisticsRepository battleStatisticsRepository;

    public void addBattleStatistics(final BattleStatistics battleStatistics) {

        battleStatisticsRepository.insert(battleStatistics);
    }

    public BattleStatistics getBattleStatistics(final String username) {

        return battleStatisticsRepository.findByUsername(username).orElseGet(() -> new BattleStatistics(username));
    }

    public List<BattleStatistics> getAllBattleStatistics() {

        return battleStatisticsRepository.findAll();
    }

    public List<BattleStatistics> getLeaderBoard(int skip) {

        List<BattleStatistics> battleStatisticsList =
                battleStatisticsRepository
                        .findAll()
                        .stream()
                        .sorted(Comparator.comparing(BattleStatistics::getVictories))
                        .skip(skip)
                        .limit(20)
                        .collect(Collectors.toList());

        return battleStatisticsList;
    }

    public void updateBattleStatistics(final PvpEvent pvpEvent) {

        if (pvpEvent.getPlayer1().getHealth().getRunning() == 0) {

            setBattleStatistics(pvpEvent.getPlayer2(), pvpEvent.getPlayer1());

        } else {

            setBattleStatistics(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());
        }
    }

    private void setBattleStatistics(Avatar winner, Avatar loser) {

        BattleStatistics battleStatisticsWinner = getBattleStatistics(winner.getUsername());

        BattleStatistics battleStatisticsLoser = getBattleStatistics(loser.getUsername());

        battleStatisticsWinner.setVictories(battleStatisticsWinner.getVictories() + 1);

        battleStatisticsWinner.setLevel(winner.getLevel());

        battleStatisticsWinner.setImageUrl(winner.getImageUrl());

        battleStatisticsLoser.setDefeats(battleStatisticsLoser.getDefeats() + 1);

        battleStatisticsLoser.setLevel(loser.getLevel());

        battleStatisticsLoser.setImageUrl(loser.getImageUrl());

        battleStatisticsRepository.save(battleStatisticsWinner);

        battleStatisticsRepository.save(battleStatisticsLoser);
    }

    public void removeBattleStatistics(final String username) {

        battleStatisticsRepository.deleteById(username);
    }

    public void removeAllBattleStatistics() {

        battleStatisticsRepository.deleteAll();
    }
}
