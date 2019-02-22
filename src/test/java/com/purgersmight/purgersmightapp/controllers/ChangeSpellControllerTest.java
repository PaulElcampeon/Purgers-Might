package com.purgersmight.purgersmightapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purgersmight.purgersmightapp.dto.ChangeSpellReqDto;
import com.purgersmight.purgersmightapp.dto.ChangeSpellResDto;
import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.SpellService;
import com.purgersmight.purgersmightapp.utils.ChangeSpellUtil;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ChangeSpellController.class, secure = false)
public class ChangeSpellControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ChangeSpellUtil changeSpellUtil;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private SpellService spellService;

    @Test
    public void changeSpell_newSpellShouldBeInPos0_Test1() throws Exception {
        ChangeSpellReqDto changeSpellReqDto = new ChangeSpellReqDto("Angie", 0, "Grendells Flame");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(changeSpellReqDto);

        Spell oldSpell = new Spell(SpellType.DAMAGE, 15, 10);

        Spell newSpell = new Spell(SpellType.DAMAGE, 10, 20);
        newSpell.setName("Grendells Flame");

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getSpellBook().getSpellList().add(oldSpell);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);
        when(spellService.getSpellByName("Grendells Flame")).thenReturn(newSpell);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(changeSpellUtil.changeSpell(changeSpellReqDto));

        mockMvc.perform(put("/change-spell")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(2)).getAvatarByUsername("Angie");
        verify(avatarService, times(2)).updateAvatar(any());
        verify(spellService, times(2)).getSpellByName("Grendells Flame");
    }

    @Test
    public void changeSpell_newSpellShouldBeInPos2_Test2() throws Exception {
        ChangeSpellReqDto changeSpellReqDto = new ChangeSpellReqDto("Angie", 2, "Grendells Flame");

        String jsonReq = ObjectMapperUtils.getObjectMapper().writeValueAsString(changeSpellReqDto);

        Spell oldSpell1 = new Spell(SpellType.DAMAGE, 15, 10);
        Spell oldSpell2 = new Spell(SpellType.HEAL, 15, 10);
        Spell oldSpell3 = new Spell(SpellType.DAMAGE, 15, 10);

        Spell newSpell = new Spell(SpellType.DAMAGE, 10, 20);
        newSpell.setName("Grendells Flame");

        Avatar avatar = Avatar.getStarterAvatar("Angie");
        avatar.getSpellBook().getSpellList().add(oldSpell1);
        avatar.getSpellBook().getSpellList().add(oldSpell2);
        avatar.getSpellBook().getSpellList().add(oldSpell3);

        when(avatarService.getAvatarByUsername("Angie")).thenReturn(avatar);
        when(spellService.getSpellByName("Grendells Flame")).thenReturn(newSpell);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(changeSpellUtil.changeSpell(changeSpellReqDto));

        mockMvc.perform(put("/change-spell")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonReq)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(avatarService, times(2)).getAvatarByUsername("Angie");
        verify(avatarService, times(2)).updateAvatar(any());
        verify(spellService, times(2)).getSpellByName("Grendells Flame");
    }
}
