package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.Avatar;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class RestoreAttributeService {

    private Logger logger = Logger.getLogger(RestoreAttributeService.class.getName());

    public void restoreHealth(Avatar avatar) {

        if (avatar.getKenjaPoints() >= 1) {

            avatar.getHealth().setRunning(avatar.getHealth().getActual());

            avatar.setKenjaPoints(avatar.getKenjaPoints() - 1);

            logger.log(Level.INFO,
                    String.format("%s just restored their health to %d KenjaPoints left %d",
                            avatar.getUsername(),
                            avatar.getHealth().getRunning(),
                            avatar.getKenjaPoints()
                    ));
        }
    }

    public void restoreManna(Avatar avatar) {

        if (avatar.getKenjaPoints() >= 1) {

            avatar.getManna().setRunning(avatar.getManna().getActual());

            avatar.setKenjaPoints(avatar.getKenjaPoints() - 1);

            logger.log(Level.INFO,
                    String.format("%s just restored their manna to %d KenjaPoints left %d",
                            avatar.getUsername(),
                            avatar.getManna().getRunning(),
                            avatar.getKenjaPoints()
                    ));
        }
    }
}
