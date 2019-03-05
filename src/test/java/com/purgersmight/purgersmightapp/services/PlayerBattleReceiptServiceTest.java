package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.BattleReceipt;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.utils.DateAndTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlayerBattleReceiptServiceTest {

    @SpyBean
    private PlayerBattleReceiptService playerBattleReceiptService;

    @Autowired
    private DateAndTimeUtil dateAndTimeUtil;

    @Test
    public void createBattleReceipt_Test1(){
        PvpEvent pvpEvent = new PvpEvent.PvpEventBuilder()
                .setPlayer1(Avatar.getStarterAvatar("Franky"))
                .setPlayer2(Avatar.getStarterAvatar("Benny"))
                .build();

        pvpEvent.getPlayer2().getHealth().setRunning(0);

        BattleReceipt battleReceipt = playerBattleReceiptService.createBattleReceipt(pvpEvent);

        assertEquals("Winner should be Franky", "Franky", pvpEvent.getPlayer1().getUsername());

        assertEquals("Winner should be Franky", "Franky", pvpEvent.getPlayer1().getUsername());

        assertNotNull(battleReceipt.getDate());

        assertNotNull(battleReceipt.getTime());
    }
}
