package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.BodyArmour;
import com.purgersmight.purgersmightapp.models.items.Weapon;

import java.util.concurrent.ThreadLocalRandom;

public class BattleUtils {

    public static class WeaponManager {

        public static int getDamagePoints(final Weapon weapon) {

            return ThreadLocalRandom.current().nextInt(weapon.getBottomDamage(), weapon.getTopDamage());
        }
    }

    public static class ArmourManager {

        public static double getFullAmourDefensePoints(final BodyArmour bodyArmour) {

            return bodyArmour.getChestArmour().getArmourPoints()
                    + bodyArmour.getHeadArmour().getArmourPoints()
                    + bodyArmour.getLegArmour().getArmourPoints();
        }
    }

    public static class DamageManager {

        public static double getDamageReduction(final double fullArmourDefensePoints) {

            if (fullArmourDefensePoints > 600) {

                return 0.6;
            }

            return fullArmourDefensePoints / 1000;
        }

        public static int getFinalDamage(final int weaponDamagePoints, double damageReduction) {

            if (damageReduction > 0.6) {

                damageReduction = 0.6;
            }

            return (int) (weaponDamagePoints * (1 - damageReduction));
        }
    }
}
