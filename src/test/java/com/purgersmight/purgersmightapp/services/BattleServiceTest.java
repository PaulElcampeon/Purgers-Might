package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.AttackPlayerResDto;
import com.purgersmight.purgersmightapp.enums.AttackType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.models.Spell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BattleServiceTest {

    @SpyBean
    private BattleService battleService;

    @MockBean
    private PvpEventService pvpEventService;

    @MockBean
    private AwardService awardService;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private PlayerBattleReceiptService playerBattleReceiptService;

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

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.MELEE, 0);

        when(pvpEventService.getPvpEventByEventId(any())).thenReturn(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertTrue(defender.getHealth().getRunning() < 100);

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());
    }

    @Test
    public void processPlayerAttackDto_attackingWithSpell_Test25() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        when(pvpEventService.getPvpEventByEventId("MockEvent")).thenReturn(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(60, defender.getHealth().getRunning().intValue());

        assertEquals(50, attacker.getManna().getRunning().intValue());

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());
    }

    @Test
    public void processPlayerAttackDto_healingWithSpell_Test26() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        attacker.getHealth().setRunning(40);

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        Spell healSpell = Spell.getDefaultHealSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(healSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        when(pvpEventService.getPvpEventByEventId("MockEvent")).thenReturn(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(80, attacker.getHealth().getRunning().intValue());

        assertEquals(50, attacker.getManna().getRunning().intValue());

        assertFalse(result.isEnded());

        assertEquals("Stanley", result.getPvpEvent().getWhosTurn());
    }

    @Test
    public void processPlayerAttackDto_eventShouldHaveEnded_Test27() {

        Avatar attacker = Avatar.getStarterAvatar("Dave");

        Avatar defender = Avatar.getStarterAvatar("Stanley");

        defender.getHealth().setRunning(10);

        Spell attackSpell = Spell.getDefaultAttackSpell(10, 40);

        attacker.getSpellBook().getSpellList().add(attackSpell);

        PvpEvent pvpEvent = new PvpEvent();

        pvpEvent.setPlayer1(attacker);

        pvpEvent.setPlayer2(defender);

        pvpEvent.setWhosTurn("Dave");

        AttackPlayerReqDto attackPlayerReqDto = new AttackPlayerReqDto("MockEvent", AttackType.SPELL, 0);

        when(pvpEventService.getPvpEventByEventId("MockEvent")).thenReturn(pvpEvent);

        AttackPlayerResDto result = battleService.processPlayerAttackDto(attackPlayerReqDto);

        assertEquals(50, attacker.getManna().getRunning().intValue());

        assertEquals(0, defender.getHealth().getRunning().intValue());

        assertTrue(result.isEnded());
    }

    @Test
    public void getWinner_shouldReturnFranky_Test28() {

        PvpEvent pvpEvent = new PvpEvent.PvpEventBuilder()
                .setPlayer1(Avatar.getStarterAvatar("Franky"))
                .setPlayer2(Avatar.getStarterAvatar("Benny"))
                .build();

        pvpEvent.getPlayer2().getHealth().setRunning(0);

        String winner = battleService.getWinner(pvpEvent);

        assertEquals("Franky", winner);
    }

}
