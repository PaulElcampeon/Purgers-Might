package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.Avatar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class LevelService {

    @Value("${levelup.bonus.experience}")
    private double experienceBonus;

    @Value("${levelup.bonus.health}")
    private double healthBonus;

    @Value("${levelup.bonus.manna}")
    private double mannaBonus;

    private Logger logger = Logger.getLogger(LevelService.class.getName());

    public void levelUpAvatar(Avatar avatar) {

        if (avatar.getExperience().getRunning() >= avatar.getExperience().getActual()) {

            avatar.setLevel(avatar.getLevel() + 1);

            avatar.getExperience().setRunning(avatar.getExperience().getRunning() - avatar.getExperience().getActual());

            levelAvatarAttributes(avatar);

            logger.log(Level.INFO, String.format("%s is now level %d", avatar.getUsername(), avatar.getLevel()));

            if (avatar.getExperience().getRunning() >= avatar.getExperience().getActual()) {

                levelUpAvatar(avatar);
            }
        }
    }

    public void levelAvatarAttributes(Avatar avatar) {

        avatar.getExperience().setActual((int) (avatar.getExperience().getActual() * experienceBonus));

        avatar.getHealth().setActual((int) (avatar.getHealth().getActual() * healthBonus));

        avatar.getManna().setActual((int) (avatar.getManna().getActual() * mannaBonus));
    }
}
