package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.*;
import com.purgersmight.purgersmightapp.services.BattleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BuffAndDebuffUtilsTest {

    @Autowired
    private BuffAndDebuffUtils buffAndDebuffUtils;

    @MockBean
    private BattleService battleService;

    @Test
    public void processBuffs_mannaBuff_Test1() {

        Spell spell = new Spell(SpellType.BUFF_MANNA, 10, 15, 2);

        AbstractBuffAndDebuff mannaBuff = new Buff(spell);

        Avatar avatar = Avatar.getStarterAvatar("R");

        avatar.getManna().setRunning(10);

        avatar.getBuffList().add(mannaBuff);

        buffAndDebuffUtils.processBuffs(avatar);

        assertEquals(25, avatar.getManna().getRunning().intValue());

        assertEquals(1, mannaBuff.getNoOfTurns());

        verify(battleService, times(1)).autoCorrectPlayerManna(any(Avatar.class));
    }

    @Test
    public void processBuffs_mannaBuff_noMannaBuff_Test2() {

        Avatar avatar = Avatar.getStarterAvatar("R");

        avatar.getManna().setRunning(10);

        buffAndDebuffUtils.processBuffs(avatar);

        assertEquals(10, avatar.getManna().getRunning().intValue());
    }

    @Test
    public void processBuffs_healthBuff_Test3() {

        Spell spell = new Spell(SpellType.BUFF_HEAL, 10, 15, 2);

        AbstractBuffAndDebuff healthBuff = new Buff(spell);

        Avatar avatar = Avatar.getStarterAvatar("R");

        avatar.getHealth().setRunning(10);

        avatar.getBuffList().add(healthBuff);

        buffAndDebuffUtils.processBuffs(avatar);

        assertEquals(25, avatar.getHealth().getRunning().intValue());

        assertEquals(1, healthBuff.getNoOfTurns());

        verify(battleService, times(1)).autoCorrectPlayerHealth(any(Avatar.class));
    }

    @Test
    public void processBuffs_healthBuff_noHealthBuff_Test4() {

        Avatar avatar = Avatar.getStarterAvatar("R");

        avatar.getHealth().setRunning(10);

        buffAndDebuffUtils.processBuffs(avatar);

        assertEquals(10, avatar.getHealth().getRunning().intValue());
    }

    @Test
    public void processDebuffs_stealMannaDebuff_Test5() {

        Spell spell = new Spell(SpellType.DEBUFF_STEAL_MANNA, 10, 5, 2);

        AbstractBuffAndDebuff stealMannaDebuff = new Debuff(spell);

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getManna().setRunning(50);

        actingAvatar.getDebuffList().add(stealMannaDebuff);

        Avatar defendingAvatar = Avatar.getStarterAvatar("L");

        defendingAvatar.getManna().setRunning(30);

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        assertEquals(45, actingAvatar.getManna().getRunning().intValue());

        assertEquals(1, stealMannaDebuff.getNoOfTurns());

        assertEquals(35, defendingAvatar.getManna().getRunning().intValue());

        verify(battleService, times(1)).setAvatarManna(any(Avatar.class), anyInt());

        verify(battleService, times(2)).autoCorrectPlayerManna(any(Avatar.class));
    }

    @Test
    public void processDebuffs_stealMannaDebuff_elseIf_Test6() {

        Spell spell = new Spell(SpellType.DEBUFF_STEAL_MANNA, 10, 5, 2);

        AbstractBuffAndDebuff stealMannaDebuff = new Debuff(spell);

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getManna().setRunning(4);

        actingAvatar.getDebuffList().add(stealMannaDebuff);

        Avatar defendingAvatar = Avatar.getStarterAvatar("L");

        defendingAvatar.getManna().setRunning(30);

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        assertEquals(0, actingAvatar.getManna().getRunning().intValue());

        assertEquals(1, stealMannaDebuff.getNoOfTurns());

        assertEquals(34, defendingAvatar.getManna().getRunning().intValue());

        verify(battleService, times(2)).autoCorrectPlayerManna(any(Avatar.class));
    }

    @Test
    public void processDebuffs_stealMannaDebuff_noDebuff_Test7() {

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getManna().setRunning(60);

        Avatar defendingAvatar = Avatar.getStarterAvatar("L");

        defendingAvatar.getManna().setRunning(60);

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        assertEquals(60, actingAvatar.getManna().getRunning().intValue());

        assertEquals(60, defendingAvatar.getManna().getRunning().intValue());
    }

    @Test
    public void processDebuffs_stealHealthDebuff_Test8() {

        Spell spell = new Spell(SpellType.DEBUFF_STEAL_HEALTH, 10, 5, 2);

        AbstractBuffAndDebuff stealHealthDebuff = new Debuff(spell);

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getHealth().setRunning(50);

        actingAvatar.getDebuffList().add(stealHealthDebuff);

        Avatar defendingAvatar = Avatar.getStarterAvatar("L");

        defendingAvatar.getHealth().setRunning(30);

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        assertEquals(45, actingAvatar.getHealth().getRunning().intValue());

        assertEquals(1, stealHealthDebuff.getNoOfTurns());

        assertEquals(35, defendingAvatar.getHealth().getRunning().intValue());

        verify(battleService, times(2)).autoCorrectPlayerHealth(any(Avatar.class));
    }

    @Test
    public void processDebuffs_stealHealthDebuff_elseIf_Test9() {

        Spell spell = new Spell(SpellType.DEBUFF_STEAL_HEALTH, 10, 5, 2);

        AbstractBuffAndDebuff stealHealthDebuff = new Debuff(spell);

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getHealth().setRunning(4);

        actingAvatar.getDebuffList().add(stealHealthDebuff);

        Avatar defendingAvatar = Avatar.getStarterAvatar("L");

        defendingAvatar.getHealth().setRunning(30);

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        assertEquals(0, actingAvatar.getHealth().getRunning().intValue());

        assertEquals(1, stealHealthDebuff.getNoOfTurns());

        assertEquals(34, defendingAvatar.getHealth().getRunning().intValue());

        verify(battleService, times(2)).autoCorrectPlayerHealth(any(Avatar.class));
    }

    @Test
    public void processDebuffs_stealHealthDebuff_noDebuff_Test10() {

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getHealth().setRunning(100);

        Avatar defendingAvatar = Avatar.getStarterAvatar("L");

        defendingAvatar.getHealth().setRunning(100);

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        assertEquals(100, actingAvatar.getHealth().getRunning().intValue());

        assertEquals(100, defendingAvatar.getHealth().getRunning().intValue());
    }

    @Test
    public void processDebuffs_stealHealthDebuff_Test11() {

        Spell spell = new Spell(SpellType.DEBUFF_DAMAGE, 10, 5, 2);

        AbstractBuffAndDebuff damageDebuff = new Debuff(spell);

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getHealth().setRunning(50);

        actingAvatar.getDebuffList().add(damageDebuff);

        buffAndDebuffUtils.processDebuffs(actingAvatar, new Avatar());

        assertEquals(45, actingAvatar.getHealth().getRunning().intValue());

        assertEquals(1, damageDebuff.getNoOfTurns());

        verify(battleService, times(1)).autoCorrectPlayerHealth(any(Avatar.class));
    }

    @Test
    public void processDebuffs_stealHealthDebuff_noDebuff_Test12() {

        Avatar actingAvatar = Avatar.getStarterAvatar("R");

        actingAvatar.getHealth().setRunning(100);

        buffAndDebuffUtils.processDebuffs(actingAvatar, new Avatar());

        assertEquals(100, actingAvatar.getHealth().getRunning().intValue());
    }

    @Test
    public void getBuffOrDeBuffOfType_notReturnNull_Test13() {

        Spell spell = new Spell(SpellType.BUFF_MANNA, 10, 5, 2);

        List<AbstractBuffAndDebuff> buffs = new ArrayList<>();

        buffs.add(new Buff(spell));

        assertTrue(buffAndDebuffUtils.getBuffOrDeBuffOfType(SpellType.BUFF_MANNA, buffs).isPresent());
    }

    @Test
    public void getBuffOrDeBuffOfType_returnNull_Test14() {

        Spell spell = new Spell(SpellType.BUFF_MANNA, 10, 5, 2);

        List<AbstractBuffAndDebuff> buffs = new ArrayList<>();

        buffs.add(new Buff(spell));

        assertFalse(buffAndDebuffUtils.getBuffOrDeBuffOfType(SpellType.BUFF_HEAL, buffs).isPresent());
    }

    @Test
    public void getDefenseBonusFromBuff_returnNon0Number_Test15() {

        Spell spell = new Spell(SpellType.BUFF_DEFENSE, 10, 5);

        List<AbstractBuffAndDebuff> buffs = new ArrayList<>();

        buffs.add(new Buff(spell));

        assertEquals(5, buffAndDebuffUtils.getDefenseBonusFromBuff(buffs));
    }

    @Test
    public void getDefenseBonusFromBuff_return0_Test16() {

        List<AbstractBuffAndDebuff> buffs = new ArrayList<>();

        assertEquals(0, buffAndDebuffUtils.getDefenseBonusFromBuff(buffs));
    }

    @Test
    public void getDamageFromWeaponBuffs_returnNon0Number_Test17() {

        Avatar avatar = Avatar.getStarterAvatar("E");

        Spell spell = new Spell(SpellType.BUFF_DAMAGE, 10, 20, 3);

        avatar.getBuffList().add(new Buff(spell));

        assertEquals(20, buffAndDebuffUtils.getDamageFromWeaponBuffs(avatar));

        assertEquals(2, avatar.getBuffList().get(0).getNoOfTurns());
    }

    @Test
    public void getDamageFromWeaponBuffs_0_Test18() {

        Avatar avatar = Avatar.getStarterAvatar("E");

        assertEquals(0, buffAndDebuffUtils.getDamageFromWeaponBuffs(avatar));
    }

    @Test
    public void getRidOfExpiredBuffsOrDebuffs_Test19() {

        List<AbstractBuffAndDebuff> buffs = new ArrayList<>();

        Spell spell1 = new Spell(SpellType.BUFF_MANNA,10,15,0);

        Spell spell2 = new Spell(SpellType.BUFF_DAMAGE,10,15,2);

        AbstractBuffAndDebuff buff1 = new Buff(spell1);

        AbstractBuffAndDebuff buff2 = new Buff(spell2);

        buffs.add(buff1);

        buffs.add(buff2);

        buffAndDebuffUtils.getRidOfExpiredBuffsOrDebuffs(buffs);

        assertTrue(buffs.contains(buff2));

        assertFalse(buffs.contains(buff1));

        assertEquals(1, buffs.size());
    }
}
