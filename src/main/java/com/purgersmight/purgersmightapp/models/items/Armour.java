package com.purgersmight.purgersmightapp;

import lombok.Data;

@Data
public class Armour implements Item {

    private ArmourType armourType;
    private String name;
    private String imageUrl;
    private int armourPoints;

}
