package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public class AwardService {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private LevelService levelService;

    @Value("${kenja.points.pvp}")
    private int kenjaPointsPvpAward;

    public void awardWinningPlayer(PvpEvent pvpEvent) {

        if (pvpEvent.getPlayer1().getHealth().getRunning() == 0) {

            awardExperienceAndLevelAndKenjaPoints(pvpEvent.getPlayer2(), pvpEvent.getPlayer1());

        } else {

            awardExperienceAndLevelAndKenjaPoints(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());
        }
    }

    public void awardExperienceAndLevelAndKenjaPoints(Avatar winner, Avatar loser) {

        int awardedExperience = experienceService.getExperience(winner.getLevel(), loser.getLevel());

        winner.getExperience().setRunning(winner.getExperience().getRunning() + awardedExperience);

        levelService.levelUpAvatar(winner);

        awardKenjaPoints(winner);
    }

    public void awardKenjaPoints(Avatar avatar) {

        avatar.setKenjaPoints(avatar.getKenjaPoints() + kenjaPointsPvpAward);
    }
}
