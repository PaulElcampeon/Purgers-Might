package com.purgersmight.purgersmightapp.models.items;

import lombok.Data;

@Data
public class Weapon implements Item {

    private String name;
    private String imageUrl = "../images/woodenSword.png";
    private int topDamage;
    private int bottomDamage;

    public Weapon(){}

    public Weapon(String name,int topDamage, int bottomDamage){
        this.name = name;
        this.topDamage = topDamage;
        this.bottomDamage = bottomDamage;
    }

    public Weapon(final WeaponBuilder weaponBuilder){
        this.name = weaponBuilder.name;
        this.imageUrl = weaponBuilder.imageUrl;
        this.topDamage = weaponBuilder.topDamage;
        this.bottomDamage = weaponBuilder.bottomDamage;
    }

    public static Weapon getStarterWeapon(){
        return new Weapon("Wooden Sword",8,4);
    }

    public static class WeaponBuilder {

        private String name;
        private String imageUrl;
        private int topDamage;
        private int bottomDamage;

        public WeaponBuilder setWeaponName(final String weaponName){
            name = weaponName;
            return this;
        }

        public WeaponBuilder setWeaponImageUrl(final String weaponImageUrl){
            imageUrl = weaponImageUrl;
            return this;
        }

        public WeaponBuilder setWeaponTopDamage(final int weaponTopDamage){
            topDamage = weaponTopDamage;
            return this;
        }

        public WeaponBuilder setWeaponBottomDamage(final int weaponBottomDamage){
            bottomDamage = weaponBottomDamage;
            return this;
        }

        public Weapon build(){
            return new Weapon(this);
        }
    }
}
