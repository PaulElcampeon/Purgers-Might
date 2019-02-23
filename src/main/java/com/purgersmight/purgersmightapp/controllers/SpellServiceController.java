package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpellServiceController {

    @Autowired
    private SpellService spellService;

    @RequestMapping(value = "/spell-service/all-spells", method = RequestMethod.GET)
    public List<Spell> getAllSpells(){

        return spellService.getSpells();
    }

}
