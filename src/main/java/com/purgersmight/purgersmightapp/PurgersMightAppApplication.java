package com.purgersmight.purgersmightapp;

import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.PvpEventRepository;
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

	@Autowired
	private UserService userService;
//
	@Autowired
	private AvatarService avatarService;

	@Autowired
	private PvpEventRepository pvpEventRepository;

	@Autowired
    private SpellService spellService;

    @PostConstruct
    public void init() {
		pvpEventRepository.deleteAll();
		avatarService.removeAllAvatars();
		userService.removeAllUsers();
		spellService.removeAll();
//
		User angie1 = new User("Trunks", "123456");
		userService.addUser(angie1);
		Avatar angie1Ava = Avatar.getStarterAvatar("Trunks");
		angie1Ava.setImageUrl("../images/potrait1.gif");
//
        User angie2 = new User("Goku", "123456");
        userService.addUser(angie2);
        Avatar angie2Ava = Avatar.getStarterAvatar("Goku");
        angie2Ava.setImageUrl("../images/potrait2.gif");

        User angie3 = new User("Roshi", "123456");
		userService.addUser(angie3);
        Avatar angie3Ava = Avatar.getStarterAvatar("Roshi");
        angie3Ava.setImageUrl("../images/potrait3.gif");

        User angie4 = new User("Bulma", "123456");
        userService.addUser(angie4);
        Avatar angie4Ava = Avatar.getStarterAvatar("Bulma");
        angie4Ava.setImageUrl("../images/potrait4.gif");
//
        Spell fireStarter = new Spell("Fire Starter", SpellType.DEBUFF_DAMAGE, "../images/spells/fireStarter1.gif", 20, 5, "Burns the target for 5 damage each turn for 3 turns",1,3);
        Spell fireBall = new Spell("Fire Ball", SpellType.DAMAGE, "../images/spells/fire1.png", 15, 20, "Hits the target with a fireball",1,1);
        Spell marysPrayer = new Spell("Marys Prayer", SpellType.HEAL, "../images/spells/heal2.png", 30, 20, "Heals the caster for 20 hp",1,1);
        Spell fieryWeapon = new Spell("Fiery Weapon", SpellType.BUFF_DAMAGE, "../images/spells/fireyWeapon1.gif", 10, 3, "Covers your melee weapon with fire increasing its damage by 3 each time you hit the enemy for 3 turns",1,3);
        Spell twistedThorns = new Spell("Twisted Thorns", SpellType.DEBUFF_DAMAGE, "../images/spells/twistedThorns1.gif", 10, 4, "Strangles the target for 5 damage each turn for 3 turns",1,2);
        Spell radiantLight = new Spell("Radiant Light", SpellType.BUFF_HEAL, "../images/spells/radiantLight1.png", 10, 5, "Heals the caster for 5 hp every turn for 3 turns",1,3);

        angie1Ava.getSpellBook().getSpellList().add(fireBall);
        angie1Ava.getSpellBook().getSpellList().add(fireStarter);
        angie1Ava.getSpellBook().getSpellList().add(radiantLight);

        angie2Ava.getSpellBook().getSpellList().add(marysPrayer);
        angie2Ava.getSpellBook().getSpellList().add(fieryWeapon);
        angie2Ava.getSpellBook().getSpellList().add(twistedThorns);

        spellService.addSpell(fireStarter);
        spellService.addSpell(fireBall);
        spellService.addSpell(marysPrayer);
        spellService.addSpell(fieryWeapon);


        avatarService.addAvatar(angie1Ava);
        avatarService.addAvatar(angie2Ava);
        avatarService.addAvatar(angie3Ava);
        avatarService.addAvatar(angie4Ava);



//
//		PvpEvent pvpEvent = new PvpEvent();
//		pvpEvent.setEventId("rews");
//		pvpEventRepository.insert(pvpEvent);

    }


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PurgersMightAppApplication.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        for(String beanName: beanNames){
            System.out.println(beanName);
        }
    }
}

