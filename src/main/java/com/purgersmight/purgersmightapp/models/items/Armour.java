package com.purgersmight.purgersmightapp.models.items;

import com.purgersmight.purgersmightapp.enums.ArmourType;
import lombok.Data;

@Data
public class Armour implements Item {

    private String name;
    private String imageUrl;
    private int armourPoints;
    private ArmourType armourType;

    public Armour(){}

    public Armour(String name, int armourPoints, ArmourType armourType){
        this.name = name;
        this.armourPoints = armourPoints;
        this.armourType = armourType;
    }

    public Armour(String name, String imageUrl, int armourPoints, ArmourType armourType){
        this.name = name;
        this.imageUrl = imageUrl;
        this.armourPoints = armourPoints;
        this.armourType = armourType;
    }

    public Armour(final ArmourBuilder armourBuilder){
        this.name = armourBuilder.name;
        this.imageUrl = armourBuilder.imageUrl;
        this.armourPoints = armourBuilder.armourPoints;
        this.armourType = armourBuilder.armourType;
    }

    public static class ArmourBuilder {

        private String name;
        private String imageUrl;
        private int armourPoints;
        private ArmourType armourType;

        public ArmourBuilder setArmourName(final String armourName){
            name = armourName;
            return this;
        }

        public ArmourBuilder setArmourImageUrl(final String armourImageUrl){
            imageUrl = armourImageUrl;
            return this;
        }

        public ArmourBuilder setArmourPoints(final int armourPoints){
            this.armourPoints = armourPoints;
            return this;
        }

        public ArmourBuilder setArmourType(final ArmourType armourType){
            this.armourType = armourType;
            return this;
        }

        public Armour build(){
            return new Armour(this);
        }
    }

}
