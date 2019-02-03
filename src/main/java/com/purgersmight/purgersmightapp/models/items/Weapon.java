package com.purgersmight.purgersmightapp;

import lombok.Data;

@Data
public class Weapon implements Item {
    
    private String name;
    private String imageUrl;
    private int topDamage;
    private int bottomDamage;

}
