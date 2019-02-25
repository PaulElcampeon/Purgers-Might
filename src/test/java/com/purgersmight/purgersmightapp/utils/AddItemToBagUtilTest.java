package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.Bag;
import com.purgersmight.purgersmightapp.models.items.Armour;
import com.purgersmight.purgersmightapp.models.items.Item;
import com.purgersmight.purgersmightapp.models.items.Weapon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnit4.class)
public class AddItemToBagUtilTest {

    @Test
    public void addItemToBag_trueWithItemAdded_Test1() {

        Bag bag = new Bag();

        Item item = new Weapon().getStarterWeapon();

        assertTrue(AddItemToBagUtil.addItemToBag(bag, item));

    }

    @Test
    public void addItemToBag_falseWithItemNotAdded_Test2() {

        Bag bag = new Bag();

        Item item1 = new Weapon().getStarterWeapon();
        Item item2 = new Weapon().getStarterWeapon();
        Item item3 = new Weapon().getStarterWeapon();
        Item item4 = new Weapon().getStarterWeapon();
        Item item5 = new Weapon().getStarterWeapon();
        Item item6 = new Weapon().getStarterWeapon();
        Item item7 = new Weapon().getStarterWeapon();
        Item item8 = new Weapon().getStarterWeapon();
        Item item9 = new Weapon().getStarterWeapon();
        Item item10 = new Armour("sa", 10, ArmourType.HEAD);

        bag.getInventory().add(item1);
        bag.getInventory().add(item2);
        bag.getInventory().add(item3);
        bag.getInventory().add(item4);
        bag.getInventory().add(item5);
        bag.getInventory().add(item6);
        bag.getInventory().add(item7);
        bag.getInventory().add(item8);
        bag.getInventory().add(item9);

        assertFalse(AddItemToBagUtil.addItemToBag(bag, item10));

        assertFalse(bag.getInventory().contains(item10));

    }
}

