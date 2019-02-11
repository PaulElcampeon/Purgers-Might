package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.BodyArmour;
import com.purgersmight.purgersmightapp.models.items.Weapon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class BattleUtilsTest {

    @Test
    public void getDamagePoints_shouldReturnNumberBetween_Test1() {
        Weapon weapon1 = new Weapon.WeaponBuilder().setWeaponBottomDamage(5).setWeaponTopDamage(10).build();
        int result1 = BattleUtils.WeaponManager.getDamagePoints(weapon1);

        Weapon weapon2 = new Weapon.WeaponBuilder().setWeaponBottomDamage(7).setWeaponTopDamage(23).build();
        int result2 = BattleUtils.WeaponManager.getDamagePoints(weapon2);

        assertTrue(result1 >= 5 && result1 <= 10);
        assertTrue(result2 >= 7 && result2 <= 23);

    }

    @Test
    public void getFullAmourDefensePoints_shouldReturn30_Test2() {
        BodyArmour bodyArmour = BodyArmour.getStarterBodyArmour(10, 10, 10);
        double result = BattleUtils.ArmourManager.getFullAmourDefensePoints(bodyArmour);

        assertEquals(30, result);
    }

    @Test
    public void getDamageReduction_shouldReturnZeroPoint03_Test3() {
        double result = BattleUtils.DamageManager.getDamageReduction(30);

        assertEquals(0.03, result);
    }

    @Test
    public void getDamageReduction_shouldReturnZeroPoint5_Test4() {
        double result = BattleUtils.DamageManager.getDamageReduction(700);

        assertEquals(0.6, result);
    }

    @Test
    public void getFinalDamage_shouldReturn7_Test5() {
        int result = BattleUtils.DamageManager.getFinalDamage(10, 0.3);

        assertEquals(7, result);
    }

    @Test
    public void getFinalDamage_shouldReturn10_Test6() {
        int result = BattleUtils.DamageManager.getFinalDamage(10, 0.03);

        assertEquals(9, result);
    }
}
