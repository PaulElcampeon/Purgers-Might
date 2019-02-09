package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.SpellType;
import lombok.Data;

@Data
public class Spell {

    private SpellType spellType;
    private String name;
    private String imageUrl;
    private int mannaCost;
    private int damagePoints;
    private String description;

}
