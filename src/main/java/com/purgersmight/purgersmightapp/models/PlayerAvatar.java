package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.models.items.Weapon;
import lombok.Data;

@Data
public class PlayerAvatar extends Avatar {

    private AvatarAttribute<Integer> experience;
    private int kenjaPoints;
    private boolean active;
    private Weapon weapon;
    private BodyArmour bodyArmour;
    private boolean inEvent;
    private String eventId;
    private boolean hasInvitation;
    private Bag bag;

    public PlayerAvatar(){}

    public PlayerAvatar(PlayerAvatarBuilder playerAvatarBuilder){
        setUserName(playerAvatarBuilder.userName);
        setImageUrl(playerAvatarBuilder.imageUrl);
        setHealth(playerAvatarBuilder.health);
        setManna(playerAvatarBuilder.manna);
        setWeapon(playerAvatarBuilder.weapon);
        setBodyArmour(playerAvatarBuilder.bodyArmour);
        setBag(playerAvatarBuilder.bag);
        setLevel(playerAvatarBuilder.level);
        setExperience(playerAvatarBuilder.experience);
        setSpellBook(playerAvatarBuilder.spellBook);
        setHasInvitation(playerAvatarBuilder.hasInvitation);
        setInEvent(playerAvatarBuilder.inEvent);
        setEventId(playerAvatarBuilder.eventId);
        setActive(playerAvatarBuilder.active);
        setKenjaPoints(playerAvatarBuilder.kenjaPoints);
    }

    public static PlayerAvatar getStarterAvatar(final String username,final String imageUrl){
      return new PlayerAvatarBuilder()
              .setAvatarUsername(username)
              .setAvatarImageUrl(imageUrl)
              .build();
    }

    public static class PlayerAvatarBuilder {

        private String userName;
        private String imageUrl;
        private int level = 1;
        private int kenjaPoints = 0;
        private AvatarAttribute<Integer> health = new AvatarAttribute<>(100);
        private AvatarAttribute<Integer> manna = new AvatarAttribute<>(60);
        private AvatarAttribute<Integer> experience = new AvatarAttribute<>(0);
        private SpellBook spellBook;
        private Weapon weapon = Weapon.getStarterWeapon();
        private BodyArmour bodyArmour = BodyArmour.getStarterBodyArmour();
        private boolean inEvent = false;
        private String eventId;
        private boolean hasInvitation = false;
        private boolean active = false;
        private Bag bag = new Bag();

        public PlayerAvatarBuilder setAvatarUsername(final String avatarUsername){
            userName = avatarUsername;
            return this;
        }

        public PlayerAvatarBuilder setAvatarImageUrl(final String avatarImageUrl){
            imageUrl = avatarImageUrl;
            return this;
        }

        public PlayerAvatarBuilder setAvatarHealth(final int avatarHealthValue){
            health = new AvatarAttribute<>(avatarHealthValue);
            return this;
        }

        public PlayerAvatarBuilder setAvatarManna(final int avatarMannaValue){
            manna = new AvatarAttribute<>(avatarMannaValue);
            return this;
        }

        public PlayerAvatarBuilder setAvatarExperience(final int avatarExperienceValue){
            experience = new AvatarAttribute<>(avatarExperienceValue);
            return this;
        }

        public PlayerAvatarBuilder setAvatarLevel(final int avatarLevel){
            level = avatarLevel;
            return this;
        }

        public PlayerAvatarBuilder setAvatarSpellBook(final SpellBook avatarSpellBook){
            spellBook = avatarSpellBook;
            return this;
        }

        public PlayerAvatarBuilder setAvatarWeapon(final Weapon avatarWeapon){
            weapon = avatarWeapon;
            return this;
        }

        public PlayerAvatarBuilder setAvatarBodyArmour(final BodyArmour avatarBodyArmour){
            bodyArmour = avatarBodyArmour;
            return this;
        }

        public PlayerAvatarBuilder setAvatarBag(final Bag avatarBag){
            bag = avatarBag;
            return this;
        }

        public PlayerAvatarBuilder setAvatarKenjaPoints(final int avatarKenjaPoints){
             kenjaPoints = avatarKenjaPoints;
            return this;
        }

        public PlayerAvatar build(){
            return new PlayerAvatar(this);
        }
    }
}
