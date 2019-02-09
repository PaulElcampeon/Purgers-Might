package com.purgersmight.purgersmightapp;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PurgersMightAppApplication {

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserService userService;

	@Autowired
	private AvatarService avatarService;

	@PostConstruct
	public void init(){
		avatarService.removeAllAvatars();
		userService.removeAllUsers();
		User newUser = new User("Angie1", "123456");
		userService.addUser(newUser);
		avatarService.addAvatar(Avatar.getStarterAvatar("Angie1"));
	}


	public static void main(String[] args) {
		SpringApplication.run(PurgersMightAppApplication.class, args);
	}

}

