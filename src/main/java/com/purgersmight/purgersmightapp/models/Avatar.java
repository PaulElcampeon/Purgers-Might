package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.models.items.Weapon;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "AVATARS")
public class Avatar {

    @Id
    private String username;
    private String imageUrl = "../images/blankUser.png";
    private int level = 1;
    private AvatarAttribute<Integer> health = new AvatarAttribute<>(100);
    private AvatarAttribute<Integer> manna = new AvatarAttribute<>(60);
    private AvatarAttribute<Integer> experience;
    private SpellBook spellBook;
    private int kenjaPoints;
    private boolean active;
    private Weapon weapon;
    private BodyArmour bodyArmour;
    private boolean inEvent;
    private String eventId;
    private boolean hasInvitation;
    private Bag bag;

    public Avatar(){}

    public Avatar(AvatarBuilder AvatarBuilder){
        setUsername(AvatarBuilder.userName);
        setImageUrl(AvatarBuilder.imageUrl);
        setHealth(AvatarBuilder.health);
        setManna(AvatarBuilder.manna);
        setWeapon(AvatarBuilder.weapon);
        setBodyArmour(AvatarBuilder.bodyArmour);
        setBag(AvatarBuilder.bag);
        setLevel(AvatarBuilder.level);
        setExperience(AvatarBuilder.experience);
        setSpellBook(AvatarBuilder.spellBook);
        setHasInvitation(AvatarBuilder.hasInvitation);
        setInEvent(AvatarBuilder.inEvent);
        setEventId(AvatarBuilder.eventId);
        setActive(AvatarBuilder.active);
        setKenjaPoints(AvatarBuilder.kenjaPoints);
    }

    public static Avatar getStarterAvatar(final String username){
        return new Avatar.AvatarBuilder()
                .setAvatarUsername(username)
                .build();
    }

    public static class AvatarBuilder {
        private String userName;
        private String imageUrl;
        private int level = 1;
        private int kenjaPoints = 0;
        private AvatarAttribute<Integer> health = new AvatarAttribute<>(100);
        private AvatarAttribute<Integer> manna = new AvatarAttribute<>(60);
        private AvatarAttribute<Integer> experience = new AvatarAttribute<>(0);
        private SpellBook spellBook = new SpellBook();
        private Weapon weapon = Weapon.getStarterWeapon();
        private BodyArmour bodyArmour = BodyArmour.getStarterBodyArmour();
        private boolean inEvent = false;
        private String eventId;
        private boolean hasInvitation = false;
        private boolean active = false;
        private Bag bag = new Bag();

        public AvatarBuilder setAvatarUsername(final String avatarUsername){
            userName = avatarUsername;
            return this;
        }

        public AvatarBuilder setAvatarImageUrl(final String avatarImageUrl){
            imageUrl = avatarImageUrl;
            return this;
        }

        public AvatarBuilder setAvatarHealth(final int avatarHealthValue){
            health = new AvatarAttribute<>(avatarHealthValue);
            return this;
        }

        public AvatarBuilder setAvatarManna(final int avatarMannaValue){
            manna = new AvatarAttribute<>(avatarMannaValue);
            return this;
        }

        public AvatarBuilder setAvatarExperience(final int avatarExperienceValue){
            experience = new AvatarAttribute<>(avatarExperienceValue);
            return this;
        }

        public AvatarBuilder setAvatarLevel(final int avatarLevel){
            level = avatarLevel;
            return this;
        }

        public AvatarBuilder setAvatarSpellBook(final SpellBook avatarSpellBook){
            spellBook = avatarSpellBook;
            return this;
        }

        public AvatarBuilder setAvatarWeapon(final Weapon avatarWeapon){
            weapon = avatarWeapon;
            return this;
        }

        public AvatarBuilder setAvatarBodyArmour(final BodyArmour avatarBodyArmour){
            bodyArmour = avatarBodyArmour;
            return this;
        }

        public AvatarBuilder setAvatarBag(final Bag avatarBag){
            bag = avatarBag;
            return this;
        }

        public AvatarBuilder setAvatarKenjaPoints(final int avatarKenjaPoints){
            kenjaPoints = avatarKenjaPoints;
            return this;
        }

        public AvatarBuilder setAvatarEventId(final String avatarEventId){
            eventId = avatarEventId;
            return this;
        }

        public Avatar build(){
            return new Avatar(this);
        }
    }

}
