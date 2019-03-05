package com.purgersmight.purgersmightapp.models;

import lombok.Data;

@Data
public class Debuff extends AbstractBuffAndDebuff {

    public Debuff() {
    }

    public Debuff(Spell spell) {
        super(spell);
    }
}
