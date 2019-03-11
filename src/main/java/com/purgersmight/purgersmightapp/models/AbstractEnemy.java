package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.utils.BattleUtils;
import lombok.Data;

import java.util.List;

@Data
public class AbstractEnemy {

    private String name;
    private String imageUrl;
    private AvatarAttribute<Integer> health;
    private int hitDamage;
    private int defensePoints;
    private List<AbstractBuffAndDebuff> debuffList;

    public void attack(Avatar avatar) {

        double avatarDefense = BattleUtils.ArmourManager.getFullAmourDefensePoints(avatar.getBodyArmour());

        double damageReduction = BattleUtils.DamageManager.getDamageReduction(avatarDefense);

        int finalDamage = BattleUtils.DamageManager.getFinalDamage(this.hitDamage, damageReduction);

        avatar.getHealth().setRunning(avatar.getHealth().getRunning() - finalDamage);
    }
}
