package com.purgersmight.purgersmightapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class ExperienceService {

    private Logger logger = Logger.getLogger(ExperienceService.class.getName());

    @Value("${experience.accumulator}")
    private int experienceAccumulator;

    public int getExperience(int levelOfWinner, int levelOfLoser){
        int differenceInLevel = levelOfWinner - levelOfLoser;
        int awardedExperience;
        if(differenceInLevel >= 5 || differenceInLevel <=-5){
            awardedExperience = 0;
        } else {
            if(differenceInLevel >= 0){
                awardedExperience = 20 - differenceInLevel*experienceAccumulator;
            } else {
                awardedExperience = 20 + Math.abs(differenceInLevel)*experienceAccumulator;
            }
        }
        logger.log(Level.INFO, String.format("Player was awarded %d experience points",awardedExperience));
        return awardedExperience;
    }
}
