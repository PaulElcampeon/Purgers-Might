package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.SpellType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "SPELLS")
public class Spell {

    private String name;
    private SpellType spellType;
    private String imageUrl;
    private int mannaCost;
    private int damagePoints;
    private String description;
    private int spellLevel;

    public Spell() {
    }

    public Spell(SpellType spellType, int mannaCost, int damagePoints) {
        this.spellType = spellType;
        this.mannaCost = mannaCost;
        this.damagePoints = damagePoints;
    }

    public static Spell getDefaultAttackSpell() {
        return new Spell(SpellType.DAMAGE, 10, 15);
    }

    public static Spell getDefaultAttackSpell(int mannaCost, int damagePoints) {
        return new Spell(SpellType.DAMAGE, mannaCost, damagePoints);
    }

    public static Spell getDefaultHealSpell() {
        return new Spell(SpellType.HEAL, 20, 20);
    }

    public static Spell getDefaultHealSpell(int mannaCost, int damagePoints) {
        return new Spell(SpellType.HEAL, mannaCost, damagePoints);
    }
}
