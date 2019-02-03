package com.purgersmight.purgersmightapp;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.AvatarAttribute;
import lombok.Data;

@Data
public class PlayerAvatar extends Avatar {

    private AvatarAttribute<Integer> experience;
    private boolean active;
    private Weapon weapon;
    private BodyArmour armour;
    private boolean inEvent;
    private String eventId;
    private boolean hasInvitation;
    private Bag bag;

}
