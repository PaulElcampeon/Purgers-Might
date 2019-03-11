package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.AbstractEnemy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnemyCreator {

    @Autowired
    private EnemyCreatorUtils enemyCreatorUtils;

    public AbstractEnemy createEnemy(final int level) {

        AbstractEnemy enemy = enemyCreatorUtils.getEnemy();

        enemy.setName(enemyCreatorUtils.getName());

        enemy.setImageUrl(enemyCreatorUtils.getImageUrl());

        enemy.setHealth(enemyCreatorUtils.getHealth(level));

        enemy.setHitDamage(enemyCreatorUtils.getHitDamage(level));

        enemy.setDefensePoints(enemyCreatorUtils.getDefensePoints(level));

        return enemy;
    }
}
