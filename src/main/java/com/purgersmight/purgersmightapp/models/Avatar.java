package com.purgersmight.purgersmightapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class Avatar {

    @Id
    private String username;
    private String imageUrl;
    private int level = 1;
    private AvatarAttribute<Integer> health = new AvatarAttribute<>(100);
    private AvatarAttribute<Integer> manna = new AvatarAttribute<>(60);
    private SpellBook spellBook;

}
