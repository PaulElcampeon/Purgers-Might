package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purgersmight.purgersmightapp.services.ExperienceService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperienceServiceController {

    @Autowired
    private ExperienceService experienceService;

    @RequestMapping(value = "/experience-service/get-experience/{levelOfWinner}/{levelOfLoser}", method = RequestMethod.GET)
    public ResponseEntity<String> getExperiencePoint(@PathVariable final int levelOfWinner, @PathVariable final int levelOfLoser) throws JsonProcessingException {

        String experienceAsString = ObjectMapperUtils.getObjectMapper().writeValueAsString(experienceService.getExperience(levelOfWinner, levelOfLoser));

        return new ResponseEntity<>(
                experienceAsString,
                HttpStatus.OK);
    }
}
