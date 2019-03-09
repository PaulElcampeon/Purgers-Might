package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "singleton")
public class SpellService {

    @Autowired
    private SpellRepository spellRepository;

    public Spell getSpellByName(final String name) {

        return spellRepository.findByName(name).get();
    }

    public void addSpell(final Spell spell) {

        spellRepository.insert(spell);
    }

    public List<Spell> getSpells() {

        return spellRepository.findAll();
    }

    public void removeAll() {

        spellRepository.deleteAll();
    }

}
