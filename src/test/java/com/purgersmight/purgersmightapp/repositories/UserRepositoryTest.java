package com.purgersmight.purgersmightapp.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Before
    public void clearRepository1(){
        userRepository.deleteAll();
    }

    @After
    public void clearRepository(){
        userRepository.deleteAll();
    }

    @Test
    public void addUser_Test1(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userRepository.insert(user);

        long numberOfUsers = userRepository.count();

        assertEquals(1, numberOfUsers);
    }

    @Test
    public void addUser_Test2(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userRepository.insert(user);

        assertThrows(DuplicateKeyException.class,()->{
            userRepository.insert(user);
        });
    }

    @Test
    public void updateUser_Test3(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userRepository.insert(user);
        user.setPassword("0000");

        userRepository.save(user);
        User resultUser = userRepository.findByUsername("Angie").get();

        assertEquals("0000", resultUser.getPassword());
    }

    @Test
    public void findUserByUsername_Test4(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userRepository.insert(user);
        User resultUser = userRepository.findByUsername("Angie").get();

        assertEquals(user, resultUser);
    }

    @Test
    public void deleteUser_Test5(){
        User user = new User();
        user.setUsername("Angie");
        user.setPassword("43");

        userRepository.insert(user);

        userRepository.delete(user);

        assertFalse(userRepository.existsById("Angie"));
    }


}
