package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.ArmourType;
import com.purgersmight.purgersmightapp.models.items.Armour;
import lombok.Data;

@Data
public class BodyArmour {

    private Armour headArmour;
    private Armour chestArmour;
    private Armour legArmour;

    public BodyArmour(){}

    private BodyArmour(Armour headArmour, Armour chestArmour, Armour legArmour){
        this.headArmour = headArmour;
        this.chestArmour = chestArmour;
        this.legArmour = legArmour;
    }

    public static BodyArmour getStarterBodyArmour(){
        return new BodyArmour(
                new Armour("Squire's Hat","http://localhost:8080/images/hat1.png",2, ArmourType.HEAD),
                new Armour("Squire's Chest Piece", "http://localhost:8080/images/chest1.png", 3, ArmourType.CHEST),
                new Armour("Squire's Leggings", "http://localhost:8080/images/leg1.png",2,ArmourType.LEG)
                );
    }
}
