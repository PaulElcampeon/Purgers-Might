package com.purgersmight.purgersmightapp.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void addUser_Test1(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByUsername(any(String.class))).thenReturn(userOptional);

        userService.addUser(user);

        verify(userRepository, times(1)).insert(any(User.class));

        User retrievedUser = userService.getUserByUsername("Angie");

        assertEquals(user, retrievedUser);
    }

    @Test
    public void findUserByUsername_Test2(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByUsername(any(String.class))).thenReturn(userOptional);

        User retrievedUser = userService.getUserByUsername("Angie");

        assertEquals(user, retrievedUser);
    }

    @Test
    public void removeUser_Test3(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.removeUser(user);

        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    public void removeUser_Test4(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.removeUserById("Angie");

        verify(userRepository, times(1)).deleteById("Angie");
    }

    @Test
    public void updateUser_Test5(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userService.updateUser(user);

        verify(userRepository, times(1)).save(user);
    }


}
