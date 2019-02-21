package com.purgersmight.purgersmightapp;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.PvpEventRepository;
import com.purgersmight.purgersmightapp.services.AvatarService;
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

//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private AvatarService avatarService;
//
//	@Autowired
//	private PvpEventRepository pvpEventRepository;

    @PostConstruct
    public void init() {
//		pvpEventRepository.deleteAll();
//		avatarService.removeAllAvatars();
//		userService.removeAllUsers();
//
//		User angie1 = new User("Trunks", "123456");
//		userService.addUser(angie1);
//		Avatar angie1Ava = Avatar.getStarterAvatar("Trunks");
//		angie1Ava.setImageUrl("../images/potrait1.gif");
//
//        User angie2 = new User("Goku", "123456");
//        userService.addUser(angie2);
//        Avatar angie2Ava = Avatar.getStarterAvatar("Goku");
//        angie2Ava.setImageUrl("../images/potrait2.gif");
//
//        avatarService.addAvatar(angie1Ava);
//        avatarService.addAvatar(angie2Ava);
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

