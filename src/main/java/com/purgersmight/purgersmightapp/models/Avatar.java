package com.purgersmight.purgersmightapp.models;

import lombok.Data;

@Data
public abstract class Avatar {

    private String userName;
    private String imageUrl;
    private int level = 1;
    private AvatarAttribute<Integer> health = new AvatarAttribute<>(100);
    private AvatarAttribute<Integer> manna = new AvatarAttribute<>(60);
    private SpellBook spellBook;

}
