package com.purgersmight.purgersmightapp.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void addUser_Test1(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        when(userRepository.findByUsername(any(String.class))).thenReturn(user);

        userService.addUser(user);

        User retrievedUser = userService.getUserByUsername("Angie");

        assertEquals(user, retrievedUser);
    }

}
