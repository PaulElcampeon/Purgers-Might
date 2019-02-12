package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {

        userService.removeAllUsers();
    }

    @Test
    public void addUser_Test1() {
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.addUser(user);

        assertTrue(userService.existsById("Angie"));
    }

    @Test
    public void findUserByUsername_Test2() {
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.addUser(user);

        User retrievedUser = userService.getUserByUsername("Angie").get();

        assertEquals(user, retrievedUser);
    }

    @Test
    public void findUserByUsername_Test3() {
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        Optional<User> retrievedUser = userService.getUserByUsername("Angie");

        assertFalse(retrievedUser.isPresent());
    }

    @Test
    public void removeUser_Test4() {
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.addUser(user);

        assertTrue(userService.existsById("Angie"));

        userService.removeUser(user);

        assertFalse(userService.existsById("Angie"));
    }

    @Test
    public void updateUser_Test6() {
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.addUser(user);

        user.setPassword("52");

        userService.updateUser(user);

        assertEquals("52", userService.getUserByUsername("Angie").get().getPassword());
    }
}
