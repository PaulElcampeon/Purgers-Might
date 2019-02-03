package com.purgersmight.purgersmightapp;

import lombok.Data;

@Data
public class Spell {

    private SpellType spellType;
    private String name;
    private String imageUrl;
    private int mannaCost;
    private int damagePoints;

}
