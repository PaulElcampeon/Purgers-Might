package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.dto.ChangeSpellReqDto;
import com.purgersmight.purgersmightapp.dto.ChangeSpellResDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class ChangeSpellUtil {

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private SpellService spellService;

    public ChangeSpellResDto changeSpell(ChangeSpellReqDto changeSpellReqDto) {

        Avatar avatar = avatarService.getAvatarByUsername(changeSpellReqDto.getUsername());

        Spell spell = spellService.getSpellByName(changeSpellReqDto.getNameOfNewSpell());

        avatar.getSpellBook().getSpellList().remove(changeSpellReqDto.getIndexOfSpell());

        avatar.getSpellBook().getSpellList().add(changeSpellReqDto.getIndexOfSpell(), spell);

        avatarService.updateAvatar(avatar);

        return new ChangeSpellResDto(true, avatar);
    }
}
