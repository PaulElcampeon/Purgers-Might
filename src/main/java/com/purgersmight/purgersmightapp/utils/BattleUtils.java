package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.items.Weapon;

import java.util.concurrent.ThreadLocalRandom;

public class WeaponManager {

    public static int getDamage(Weapon weapon){
        return ThreadLocalRandom.current().nextInt(weapon.getBottomDamage(), weapon.getTopDamage());
    }
}
