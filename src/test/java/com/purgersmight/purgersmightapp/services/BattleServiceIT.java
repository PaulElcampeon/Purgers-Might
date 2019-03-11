package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.AttackPlayerResDto;
import com.purgersmight.purgersmightapp.enums.AttackType;
import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.*;
import com.purgersmight.purgersmightapp.utils.BuffAndDebuffUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BattleServiceIT {

    @Autowired
    private BattleService battleService;

    @Autowired
    private PvpEventService pvpEventService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private BattleStatisticsService battleStatisticsService;

    @Autowired
    private BuffAndDebuffUtils buffAndDebuffUtils;

    @After
    public void tearDown() {

        pvpEventService.removeAllPvpEvents();

        avatarService.removeAllAvatars();

        battleStatisticsService.removeAllBattleStatistics();
    }

    @Test
    public void playerAttackMelee_playersHealthShouldReduce_Test1() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        battleService.playerAttackMelee(attacker, defender);

        assertTrue(defender.getHealth().getRunning() < defender.getHealth().getActual());
    }

    @Test
    public void playerAttackSpell_defendersHealthReduceAndAttacksMannaReduce_Test2() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 20);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttackSpell(attacker, defender, attackPlayerReqDto);

        assertEquals(80, defender.getHealth().getRunning().intValue());

        assertEquals(50, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerAttackSpell_notEnoughMannaHealthAndMannaStaySame_Test3() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getManna().setRunning(5);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 20);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttackSpell(attacker, defender, attackPlayerReqDto);

        assertEquals(100, defender.getHealth().getRunning().intValue());

        assertEquals(5, attacker.getManna().getRunning().intValue());
    }


    @Test
    public void playerAttackSpellDirectToHeal_playersHealthShouldIncrease_Test4() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        attacker.getHealth().setRunning(40);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell healSpell = Spell.getDefaultHealSpell(15, 30);

        attacker.getSpellBook().getSpellList().add(healSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttackSpell(attacker, defender, attackPlayerReqDto);

        assertEquals(70, attacker.getHealth().getRunning().intValue());

        assertEquals(45, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerAttackSpellDirectToHeal_notEnoughMannaHealthMannaStaySame_Test5() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(40);

        attacker.getManna().setRunning(5);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell healSpell = Spell.getDefaultAttackSpell(10, 20);

        attacker.getSpellBook().getSpellList().add(healSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttackSpell(attacker, defender, attackPlayerReqDto);

        assertEquals(40, attacker.getHealth().getRunning().intValue());

        assertEquals(5, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerHeal_playersHealthShouldIncrease_Test6() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(40);

        Spell healSpell = Spell.getDefaultHealSpell(15, 30);

        battleService.playerHeal(attacker, healSpell);

        assertEquals(70, attacker.getHealth().getRunning().intValue());

        assertEquals(45, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerHeal_notEnoughMannaHealthMannaStaySame_Test7() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(40);

        attacker.getManna().setRunning(5);

        Spell healSpell = Spell.getDefaultAttackSpell(10, 20);

        battleService.playerHeal(attacker, healSpell);

        assertEquals(40, attacker.getHealth().getRunning().intValue());

        assertEquals(5, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void autoCorrectPlayerHealth_healthShouldBeSetToActual100_Test8() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(120);

        battleService.autoCorrectPlayerHealth(attacker);

        assertEquals(100, attacker.getHealth().getRunning().intValue());
    }

    @Test
    public void autoCorrectPlayerHealth_healthShouldBe0_Test9() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(-50);

        battleService.autoCorrectPlayerHealth(attacker);

        assertEquals(0, attacker.getHealth().getRunning().intValue());
    }

    @Test
    public void autoCorrectPlayerHealth_healthShouldNotChange_Test10() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(80);

        battleService.autoCorrectPlayerHealth(attacker);

        assertEquals(80, attacker.getHealth().getRunning().intValue());
    }

    @Test
    public void setAvatarManna_mannaShouldBeReducedTo70_Test11() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getManna().setRunning(80);

        battleService.setAvatarManna(attacker, 10);

        assertEquals(70, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void setAvatarManna_mannaShouldBeCorrectedTo0_Test12() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getManna().setRunning(30);

        battleService.setAvatarManna(attacker, 40);

        assertEquals(0, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void setAvatarHealthAfterAttack_healthShouldBeReduced_Test13() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(100);

        battleService.setAvatarHealthAfterAttack(attacker, 40);

        assertEquals(60, attacker.getHealth().getRunning().intValue());
    }

    @Test
    public void setAvatarHealthAfterHeal_healthShouldBeIncreasedTo80_Test14() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(65);

        battleService.setAvatarHealthAfterHeal(attacker, 15);

        assertEquals(80, attacker.getHealth().getRunning().intValue());
    }

    @Test
    public void checkIfEventEnded_shouldReturnTrue_Test15() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(0);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        boolean result = battleService.checkIfEventEnded(pvpEvent);

        assertTrue(result);
    }

    @Test
    public void checkIfEventEnded_shouldReturnFalse_Test16() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        boolean result = battleService.checkIfEventEnded(pvpEvent);

        assertFalse(result);
    }

    @Test
    public void changeWhosTurn_shouldBeDavesTurn_Test17() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Stanley");

        battleService.changeWhosTurn(pvpEvent);

        assertEquals("Dave", pvpEvent.getWhosTurn());
    }

    @Test
    public void playerAttack_defendersHealthShouldBeReducedWithMelee_Test18() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        defender.getHealth().setRunning(100);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.MELEE, 0);

        battleService.playerAttack(attacker, defender, attackPlayerReqDto);

        assertTrue(defender.getHealth().getRunning() < 100);
    }

    @Test
    public void playerAttack_defendersHealthShouldBeReducedWithSpell_Test19() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        attacker.getManna().setRunning(50);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        defender.getHealth().setRunning(100);

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 20);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttack(attacker, defender, attackPlayerReqDto);

        assertEquals(80, defender.getHealth().getRunning().intValue());

        assertEquals(40, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerAttack_attackersHealthShouldBeIncreased_Test20() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        attacker.getHealth().setRunning(50);

        attacker.getManna().setRunning(40);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell healSpell = Spell.getDefaultHealSpell(10, 20);

        attacker.getSpellBook().getSpellList().add(healSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttack(attacker, defender, attackPlayerReqDto);

        assertEquals(70, attacker.getHealth().getRunning().intValue());

        assertEquals(30, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerAttack_attackersHealthShouldBeTheSameNotEnoughMannaToHeal_Test21() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(50);

        attacker.getManna().setRunning(5);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell healSpell = Spell.getDefaultHealSpell(10, 20);

        attacker.getSpellBook().getSpellList().add(healSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttack(attacker, defender, attackPlayerReqDto);

        assertEquals(50, attacker.getHealth().getRunning().intValue());

        assertEquals(5, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerAttack_defendersHealthShouldBe0WithSpellAttack_Test22() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        attacker.getManna().setRunning(50);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        defender.getHealth().setRunning(30);

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.SPELL, 0);

        battleService.playerAttack(attacker, defender, attackPlayerReqDto);

        assertEquals(0, defender.getHealth().getRunning().intValue());

        assertEquals(40, attacker.getManna().getRunning().intValue());
    }

    @Test
    public void playerAttack_defendersHealthShouldBe0WithMeleeAttack_Test23() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        defender.getHealth().setRunning(1);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto(AttackType.MELEE, 0);

        battleService.playerAttack(attacker, defender, attackPlayerReqDto);

        assertEquals(0, defender.getHealth().getRunning().intValue());

        assertEquals(60, attacker.getManna().getRunning().intValue());

        assertEquals(100, attacker.getHealth().getRunning().intValue());
    }

    @Test
    public void processPlayerAttackDto_attackingWithMelee_Test24() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.MELEE, 0);

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertTrue(result.getPvpEvent().getPlayer2().getHealth().getRunning() < 100);

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }

    @Test
    public void processPlayerAttackDto_attackingWithSpell_Test25() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(60, result.getPvpEvent().getPlayer2().getHealth().getRunning().intValue());

        assertEquals(50, result.getPvpEvent().getPlayer1().getManna().getRunning().intValue());

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }

    @Test
    public void processPlayerAttackDto_healingWithSpell_Test26() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        attacker.getHealth().setRunning(40);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell healSpell = Spell.getDefaultHealSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(healSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(80, result.getPvpEvent().getPlayer1().getHealth().getRunning().intValue());

        assertEquals(50, result.getPvpEvent().getPlayer1().getManna().getRunning().intValue());

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }

    @Test
    public void processPlayerAttackDto_eventShouldHaveEnded_Test27() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getSpellBook().getSpellList().clear();

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        avatarService.addAvatar(defender);

        defender.getHealth().setRunning(10);

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(60, result.getPvpEvent().getPlayer1().getManna().getRunning().intValue());

        assertEquals(100, result.getPvpEvent().getPlayer2().getHealth().getRunning().intValue());

        assertTrue(result.isEnded());

        //Event should no longer exist in DB
        assertFalse(pvpEventService.existsById("MockEvent"));
    }

    @Test
    public void processPlayerAttackDto_playerBattleStatsShouldHaveChanged_Test28() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        avatarService.addAvatar(defender);

        defender.getHealth().setRunning(10);

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        battleService.processPlayerAttackDto(attackPlayerReqDto);

        BattleStatistics battleStatisticsResult1 = battleStatisticsService.getBattleStatistics("Dave");

        BattleStatistics battleStatisticsResult2 = battleStatisticsService.getBattleStatistics("Stanley");

        assertEquals(1, battleStatisticsResult1.getVictories());

        assertEquals(0, battleStatisticsResult1.getDefeats());

        assertEquals(1, battleStatisticsResult2.getDefeats());

        assertEquals(0, battleStatisticsResult2.getVictories());
    }

    @Test
    public void processPlayerAttackDto_attackingWithMeleeWithDebuffs_Test29() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Spell spell1 = new Spell(SpellType.DEBUFF_DAMAGE,10,5,2);
        AbstractBuffAndDebuff abstractBuffAndDebuff1 = new Debuff(spell1);

        Spell spell2 = new Spell(SpellType.DEBUFF_STEAL_MANNA,10,10,1);
        AbstractBuffAndDebuff abstractBuffAndDebuff2 = new Debuff(spell2);

        attacker.getDebuffList().add(abstractBuffAndDebuff1);
        attacker.getDebuffList().add(abstractBuffAndDebuff2);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.MELEE, 0);

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(95, result.getPvpEvent().getPlayer1().getHealth().getRunning().intValue());

        assertEquals(50, result.getPvpEvent().getPlayer1().getManna().getRunning().intValue());

        assertEquals(1, result.getPvpEvent().getPlayer1().getDebuffList().get(0).getNoOfTurns());

        //one of the debuff should have expired only leaving 1 more debuff
        assertEquals(1, result.getPvpEvent().getPlayer1().getDebuffList().size());

        assertTrue(result.getPvpEvent().getPlayer2().getHealth().getRunning() < 100);

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }

    @Test
    public void processPlayerAttackDto_attackingWithMeleeWithBuffs_Test30() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");
        attacker.getHealth().setRunning(60);
        attacker.getManna().setRunning(30);

        Spell spell1 = new Spell(SpellType.BUFF_MANNA,10,10,2);
        AbstractBuffAndDebuff abstractBuffAndDebuff1 = new Buff(spell1);

        Spell spell2 = new Spell(SpellType.BUFF_HEAL,10,10,1);
        AbstractBuffAndDebuff abstractBuffAndDebuff2 = new Buff(spell2);

        attacker.getBuffList().add(abstractBuffAndDebuff1);
        attacker.getBuffList().add(abstractBuffAndDebuff2);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.MELEE, 0);

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(70, result.getPvpEvent().getPlayer1().getHealth().getRunning().intValue());

        assertEquals(40, result.getPvpEvent().getPlayer1().getManna().getRunning().intValue());

        assertEquals(1, result.getPvpEvent().getPlayer1().getBuffList().get(0).getNoOfTurns());

        //one of the buffs should have expired only leaving 1 more buff
        assertEquals(1, result.getPvpEvent().getPlayer1().getBuffList().size());

        assertTrue(result.getPvpEvent().getPlayer2().getHealth().getRunning() < 100);

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }

    @Test
    public void processPlayerAttackDto_attackingWithMeleeWithDebuffs_Test31() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Spell spell1 = new Spell(SpellType.DEBUFF_IMMOBILIZE,0,0,1);
        AbstractBuffAndDebuff abstractBuffAndDebuff1 = new Debuff(spell1);

        attacker.getDebuffList().add(abstractBuffAndDebuff1);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.MELEE, 0);

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(100, result.getPvpEvent().getPlayer2().getHealth().getRunning().intValue());

        //one of the debuffs should have expired leaving 0 debuffs
        assertEquals(0, result.getPvpEvent().getPlayer1().getDebuffList().size());

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }

    @Test
    public void processPlayerAttackDto_attackingWithMeleeWithBuffs_Test32() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Spell spell1 = new Spell(SpellType.BUFF_DAMAGE,10,10,2);
        AbstractBuffAndDebuff abstractBuffAndDebuff1 = new Buff(spell1);

        attacker.getBuffList().add(abstractBuffAndDebuff1);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        pvpEvent.setEventId("MockEvent");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.MELEE, 0);

        pvpEventService.addPvpEvent(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        //8-4 --> with buff 18-14
        assertTrue(result.getPvpEvent().getPlayer2().getHealth().getRunning().intValue() < 88 && result.getPvpEvent().getPlayer2().getHealth().getRunning().intValue() > 80);

        assertEquals(1, result.getPvpEvent().getPlayer1().getBuffList().get(0).getNoOfTurns());

        //one of the buffs should have expired only leaving 1 more buff
        assertEquals(1, result.getPvpEvent().getPlayer1().getBuffList().size());

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());

        //PvPEvent should still exist in DB
        assertTrue(pvpEventService.existsById("MockEvent"));

        //Avatar in the Avatar DB should be the same as the Avatar in PvPEvent DB
        assertEquals(avatarService.getAvatarByUsername("Stanley"), pvpEventService.getPvpEventByEventId("MockEvent").getPlayer2());
    }
}
