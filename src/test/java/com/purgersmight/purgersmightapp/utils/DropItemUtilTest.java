package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.Bag;
import com.purgersmight.purgersmightapp.models.items.Armour;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DropItemUtilTest {

    @Autowired
    private DropItemUtil dropItemUtil;

    @Test
    public void dropItem_Test1() {

        Bag bag = new Bag();

        Armour armour1 = new Armour.ArmourBuilder().setArmourType(ArmourType.CHEST).build();
        Armour armour2 = new Armour.ArmourBuilder().setArmourType(ArmourType.HEAD).build();

        bag.getInventory().add(armour1);
        bag.getInventory().add(armour2);

        dropItemUtil.dropItem(bag, 1);

        assertFalse(bag.getInventory().contains(armour2));
        assertTrue(bag.getInventory().contains(armour1));
    }

    @Test
    public void dropItem_indexOutOfBounds_Test2() {

        Bag bag = new Bag();

        Armour armour1 = new Armour.ArmourBuilder().setArmourType(ArmourType.CHEST).build();
        Armour armour2 = new Armour.ArmourBuilder().setArmourType(ArmourType.HEAD).build();

        bag.getInventory().add(armour1);
        bag.getInventory().add(armour2);

        Bag result = dropItemUtil.dropItem(bag, 3);

        assertEquals(bag, result);
    }
}
