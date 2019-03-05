package com.purgersmight.purgersmightapp.models;

import lombok.Data;

@Data
public class Buff extends AbstractBuffAndDebuff {

    public Buff() {
    }

    public Buff(Spell spell) {
        super(spell);
    }
}
