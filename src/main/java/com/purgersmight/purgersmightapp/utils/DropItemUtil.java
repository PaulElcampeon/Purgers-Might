package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.models.Bag;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class DropItemUtil {

    public Bag dropItem(Bag bag, int indexOfItem) {

        try {

            bag.getInventory().remove(indexOfItem);

        } catch (IndexOutOfBoundsException e) {

            return bag;
        }

        return bag;
    }
}
