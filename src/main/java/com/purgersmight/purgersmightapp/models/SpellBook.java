package com.purgersmight.purgersmightapp.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SpellBook {

    private ArrayList<Spell> spellArrayList = new ArrayList<>();
}
