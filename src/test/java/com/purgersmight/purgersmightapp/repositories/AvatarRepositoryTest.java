package com.purgersmight.purgersmightapp.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PlayerAvatar;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AvatarRepositoryTest {

    @Autowired
    private AvatarRepository avatarRepository;

    @After
    public void clearDB(){
        avatarRepository.deleteAll();
    }

    @Test
    public void insertAvatar_Test1(){
        Avatar playerAvatar = PlayerAvatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(playerAvatar);

        long resultCount = avatarRepository.count();

        assertEquals(1, resultCount);
    }

    @Test
    public void insertAvatar_Test2(){
        Avatar playerAvatar = PlayerAvatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(playerAvatar);

        assertThrows(DuplicateKeyException.class, ()->{
            avatarRepository.insert(playerAvatar);
        });
    }

    @Test
    public void findAvatarByUsername_Test3(){
        Avatar playerAvatar = PlayerAvatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(playerAvatar);

        Avatar retrievedAvatar = avatarRepository.findByUsername("Helpon").get();

        assertEquals(playerAvatar, retrievedAvatar);
    }

    @Test
    public void findAvatarById_Test4(){
        Avatar playerAvatar = PlayerAvatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(playerAvatar);

        Avatar retrievedAvatar = avatarRepository.findById("Helpon").get();

        assertEquals(playerAvatar, retrievedAvatar);
    }

    @Test
    public void findAvatarById_Test5(){
        Avatar playerAvatar = PlayerAvatar.getStarterAvatar("Helpon", "");

        avatarRepository.insert(playerAvatar);

        assertThrows(NoSuchElementException.class, ()->{
            avatarRepository.findById("Helpon1").get();
        });
    }

}
