package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.AbstractEnemy;
import com.purgersmight.purgersmightapp.models.AvatarAttribute;
import com.purgersmight.purgersmightapp.models.Enemy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class EnemyCreatorUtils {

    public AbstractEnemy getEnemy() {

        return new Enemy();
    }

    public AvatarAttribute<Integer> getHealth(final int level) {

        return new AvatarAttribute<>(100 + level * ThreadLocalRandom.current().nextInt(3,5));
    }

    public int getHitDamage(final int level) {

        return level * ThreadLocalRandom.current().nextInt(3,5);
    }

    public int getDefensePoints(final int level) {

        return level * ThreadLocalRandom.current().nextInt(4,6);
    }

    public String getImageUrl() {

        String[] imageUrls = {"../images/enemies/enemy1.gif", "../images/enemies/enemy2.gif", "../images/enemies/enemy3.gif", "../images/enemies/enemy4.gif"};

        return imageUrls[ThreadLocalRandom.current().nextInt(imageUrls.length)];
    }

    public String getName() {

        String[] names = {"Turnble", "Harvester", "Neval", "Kintlin"};

        return names[ThreadLocalRandom.current().nextInt(names.length)];
    }
}
