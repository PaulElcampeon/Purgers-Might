package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.SpellType;
import lombok.Data;

@Data
public abstract class AbstractBuffAndDebuff {

    private int noOfTurns;
    private SpellType spellType;
    private int amount;
    private String imageUrl;

    public AbstractBuffAndDebuff() {

    }

    public AbstractBuffAndDebuff(Spell spell) {
        this.noOfTurns = spell.getNoOfTurns();
        this.spellType = spell.getSpellType();
        this.amount = spell.getDamagePoints();
        this.imageUrl = spell.getImageUrl();
    }
}
