package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.dto.ChangeSpellReqDto;
import com.purgersmight.purgersmightapp.dto.ChangeSpellResDto;
import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.SpellService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ChangeSpellUtilTest {

    @SpyBean
    private ChangeSpellUtil changeSpellUtil;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private SpellService spellService;

    @Test
    public void changeSpell_newSpellShouldBeInPos0_Test1() {
        ChangeSpellReqDto changeSpellReqDto = new ChangeSpellReqDto("Angie", 0, "Grendells Flame");

        Spell oldSpell = new Spell(SpellType.DAMAGE, 15, 10);

        Spell newSpell = new Spell(SpellType.DAMAGE, 10, 20);
        newSpell.setName("Grendells Flame");

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getSpellBook().getSpellList().add(oldSpell);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);
        when(spellService.getSpellByName("Grendells Flame")).thenReturn(newSpell);

        ChangeSpellResDto result = changeSpellUtil.changeSpell(changeSpellReqDto);

        verify(avatarService, times(1)).getAvatarByUsername("Angie");
        verify(avatarService, times(1)).updateAvatar(any());
        verify(spellService, times(1)).getSpellByName("Grendells Flame");

        assertTrue(result.isSuccess());

        assertEquals(avatar.getSpellBook().getSpellList().get(0), newSpell);

        assertEquals(1, avatar.getSpellBook().getSpellList().size());
    }

    @Test
    public void changeSpell_newSpellShouldBeInPos1_Test2() {
        ChangeSpellReqDto changeSpellReqDto = new ChangeSpellReqDto("Angie", 1, "Grendells Flame");

        Spell oldSpell1 = new Spell(SpellType.DAMAGE, 15, 10);

        Spell oldSpell2 = new Spell(SpellType.HEAL, 15, 10);

        Spell newSpell = new Spell(SpellType.DAMAGE, 10, 20);
        newSpell.setName("Grendells Flame");

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getSpellBook().getSpellList().add(oldSpell1);
        avatar.getSpellBook().getSpellList().add(oldSpell2);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);
        when(spellService.getSpellByName("Grendells Flame")).thenReturn(newSpell);

        ChangeSpellResDto result = changeSpellUtil.changeSpell(changeSpellReqDto);

        verify(avatarService, times(1)).getAvatarByUsername("Angie");
        verify(avatarService, times(1)).updateAvatar(any());
        verify(spellService, times(1)).getSpellByName("Grendells Flame");

        assertTrue(result.isSuccess());

        assertEquals(avatar.getSpellBook().getSpellList().get(1), newSpell);

        assertEquals(2, avatar.getSpellBook().getSpellList().size());
    }
}
