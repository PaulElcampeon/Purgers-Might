package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.SpellType;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class SpellBook {

    private ArrayList<Spell> spellList = new ArrayList<>();

    public static SpellBook getStarterSpellBook() {

        SpellBook spellBook = new SpellBook();

        Spell fireBall = new Spell("Fire Ball", SpellType.DAMAGE, "../images/spells/fire1.png", 15, 20, "Hits the target with a fireball", 1, 1);
        Spell marysPrayer = new Spell("Marys Prayer", SpellType.HEAL, "../images/spells/heal2.png", 30, 20, "Heals the caster for 20 hp", 1, 1);
        Spell fireStarter = new Spell("Fire Starter", SpellType.DEBUFF_DAMAGE, "../images/spells/fireStarter1.gif", 20, 5, "Burns the target for 5 damage each turn for 3 turns", 1, 3);
        Spell twistedThorns = new Spell("Twisted Thorns", SpellType.DEBUFF_DAMAGE, "../images/spells/twistedThorns1.gif", 10, 4, "Strangles the target for 5 damage each turn for 3 turns",1,2);

        spellBook.getSpellList().addAll(Arrays.asList(fireBall, marysPrayer, fireStarter, twistedThorns));

        return spellBook;
    }

}
