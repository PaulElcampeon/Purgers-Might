package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.items.Armour;
import lombok.Data;

@Data
public class BodyArmour {

    private Armour headArmour;
    private Armour chestArmour;
    private Armour legArmour;

    public BodyArmour(){
    }

    private BodyArmour(Armour headArmour, Armour chestArmour, Armour legArmour){
        this.headArmour = headArmour;
        this.chestArmour = chestArmour;
        this.legArmour = legArmour;
    }

    public static BodyArmour getStarterBodyArmour(){
        return new BodyArmour(
                new Armour("Squire's Hat",null,2, ArmourType.HEAD),
                new Armour("Squire's Chest Piece", null, 3, ArmourType.CHEST),
                new Armour("Squire's Leggings", null,2,ArmourType.LEG)
                );
    }
}
