package com.purgersmight.purgersmightapp;

import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.SpellService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PurgersMightAppApplication {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AvatarService avatarService;
//
//    @Autowired
//    private SpellService spellService;
//
//    @PostConstruct
//    public void init() {
//
//        avatarService.removeAllAvatars();
//        userService.removeAllUsers();
//        spellService.removeAll();
//
//        User test1 = new User("Tester1", "tester1");
//        userService.addUser(test1);
//        Avatar test1Avatar = Avatar.getStarterAvatar("Tester1");
//        avatarService.addAvatar(test1Avatar);
//
//        User test2 = new User("Tester2", "tester2");
//        userService.addUser(test2);
//        Avatar test2Avatar = Avatar.getStarterAvatar("Tester2");
//        avatarService.addAvatar(test2Avatar);
//
//        Spell fireStarter = new Spell("Fire Starter", SpellType.DEBUFF_DAMAGE, "../images/spells/fireStarter1.gif", 20, 5, "Burns the target for 5 damage each turn for 3 turns", 1, 3);
//        Spell fireBall = new Spell("Fire Ball", SpellType.DAMAGE, "../images/spells/fire1.png", 15, 20, "Hits the target with a fireball", 1, 1);
//        Spell lightningShock = new Spell("Lightning Shock", SpellType.DAMAGE, "../images/spells/lightningShock1.gif", 10, 15, "Hits the target with an electric shock", 1, 1);
//        Spell iceBlast = new Spell("Ice Blast", SpellType.DAMAGE, "../images/spells/iceBlast1.gif", 18, 25, "Blast the target with a hail storm on ice", 1, 1);
//        Spell marysPrayer = new Spell("Marys Prayer", SpellType.HEAL, "../images/spells/heal2.png", 30, 20, "Heals the caster for 20 hp", 1, 1);
//        Spell fieryWeapon = new Spell("Fiery Weapon", SpellType.BUFF_DAMAGE, "../images/spells/fireryWeapon1.gif", 10, 3, "Covers your melee weapon with fire increasing its damage by 3 each time you hit the enemy for 3 turns", 1, 3);
//        Spell twistedThorns = new Spell("Twisted Thorns", SpellType.DEBUFF_DAMAGE, "../images/spells/twistedThorns1.gif", 10, 4, "Strangles the target for 5 damage each turn for 3 turns", 1, 2);
//        Spell radiantLight = new Spell("Radiant Light", SpellType.BUFF_HEAL, "../images/spells/radiantLight1.png", 10, 5, "Heals the caster for 5 hp every turn for 3 turns", 1, 3);
//
//        spellService.addSpell(fireStarter);
//        spellService.addSpell(fireBall);
//        spellService.addSpell(marysPrayer);
//        spellService.addSpell(fieryWeapon);
//        spellService.addSpell(lightningShock);
//        spellService.addSpell(iceBlast);
//        spellService.addSpell(twistedThorns);
//        spellService.addSpell(radiantLight);
//    }


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PurgersMightAppApplication.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}

