package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.Bag;
import com.purgersmight.purgersmightapp.models.items.Item;

public class AddItemToBagUtil {

    public static boolean addItemToBag(Bag bag, Item item) {

        if (verifyBagSpace(bag)) {

            bag.getInventory().add(item);

            return true;
        }

        return false;
    }

    private static boolean verifyBagSpace(Bag bag){

        return bag.getInventory().size()<9;
    }

}
