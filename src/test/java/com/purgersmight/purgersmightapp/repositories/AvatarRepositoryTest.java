package com.purgersmight.purgersmightapp.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.purgersmight.purgersmightapp.models.Avatar;
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

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AvatarRepositoryTest {

    @Autowired
    private AvatarRepository avatarRepository;

    @MockBean
    private UserService userService;

    @Before
    public void clearDBBefore(){
        avatarRepository.deleteAll();
    }
    @After
    public void clearDBAfter(){
        avatarRepository.deleteAll();
    }

    @Test
    public void insertAvatar_Test1(){
        Avatar avatar = Avatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(avatar);

        long resultCount = avatarRepository.count();

        assertEquals(1, resultCount);
    }

    @Test
    public void insertAvatar_Test2(){
        Avatar avatar = Avatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(avatar);

        assertThrows(DuplicateKeyException.class, ()->{
            avatarRepository.insert(avatar);
        });
    }

    @Test
    public void findAvatarByUsername_Test3(){
        Avatar avatar = Avatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(avatar);

        Avatar retrievedAvatar = avatarRepository.findByUsername("Helpon").get();

        assertEquals(avatar, retrievedAvatar);
    }

    @Test
    public void findAvatarByUsername_Test4(){
        Avatar avatar = Avatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(avatar);

        Avatar retrievedAvatar = avatarRepository.findByUsername("Helpon").get();

        assertEquals(avatar, retrievedAvatar);
    }

    @Test
    public void findAvatarById_Test5(){
        Avatar avatar = Avatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(avatar);

        assertThrows(NoSuchElementException.class, ()->{
            avatarRepository.findByUsername("Helpon1").get();
        });
    }

}
