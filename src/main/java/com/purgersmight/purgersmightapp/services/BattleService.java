package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.AttackPlayerResDto;
import com.purgersmight.purgersmightapp.enums.AttackType;
import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.utils.BattleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class BattleService {

    @Autowired
    private PvpEventService pvpEventService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private AwardService awardService;

    private Logger logger = Logger.getLogger(BattleService.class.getName());

    public AttackPlayerResDto processPlayerAttackDto(final AttackPlayerReqDto attackPlayerReqDto) {

        PvpEvent pvpEvent = pvpEventService.getPvpEventByEventId(attackPlayerReqDto.getEventId());

        Avatar actingAvatar;

        Avatar defendingAvatar;

        if (pvpEvent.getWhosTurn().equals(pvpEvent.getPlayer1().getUsername())) {

            actingAvatar = pvpEvent.getPlayer1();

            defendingAvatar = pvpEvent.getPlayer2();

        } else {

            actingAvatar = pvpEvent.getPlayer2();

            defendingAvatar = pvpEvent.getPlayer1();
        }

        playerAttack(actingAvatar, defendingAvatar, attackPlayerReqDto);

        if (checkIfEventEnded(pvpEvent)) {

            logger.log(Level.INFO, String.format("PvP Event with id %s has now ended", pvpEvent.getEventId()));

            awardService.awardWinningPlayer(pvpEvent);

            pvpEventService.removePvpEventById(pvpEvent.getEventId());

            updateAvatarsInDB(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());

            return new AttackPlayerResDto(true, pvpEvent.getWhosTurn(), pvpEvent);
        }

        changeWhosTurn(pvpEvent);

        updatePvpEventInDB(pvpEvent);

        updateAvatarsInDB(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());

        return new AttackPlayerResDto(false, null, pvpEvent);
    }

    public void playerAttack(Avatar actingAvatar, Avatar defendingAvatar, final AttackPlayerReqDto attackPlayerReqDto) {

        if (attackPlayerReqDto.getAttackType().equals(AttackType.MELEE)) {

            playerAttackMelee(actingAvatar, defendingAvatar);


        } else {

            playerAttackSpell(actingAvatar, defendingAvatar, attackPlayerReqDto);
        }
    }

    public void playerAttackMelee(Avatar actingAvatar, Avatar defendingAvatar) {

        int weaponDamage = BattleUtils.WeaponManager.getDamagePoints(actingAvatar.getWeapon());

        double fullArmourPoint = BattleUtils.ArmourManager.getFullAmourDefensePoints(defendingAvatar.getBodyArmour());

        double damageReduction = BattleUtils.DamageManager.getDamageReduction(fullArmourPoint);

        int finalDamage = BattleUtils.DamageManager.getFinalDamage(weaponDamage, damageReduction);

        setAvatarHealthAfterAttack(defendingAvatar, finalDamage);

        autoCorrectPlayerHealth(defendingAvatar);
    }

    public void playerAttackSpell(Avatar actingAvatar, Avatar defendingAvatar, final AttackPlayerReqDto attackPlayerReqDto) {

        Spell spell = actingAvatar.getSpellBook().getSpellList().get(attackPlayerReqDto.getSpellPosition());

        if (spell.getSpellType().equals(SpellType.HEAL)) {

            playerHeal(actingAvatar, spell);

        } else {

            if (actingAvatar.getManna().getRunning() >= spell.getMannaCost()) {

                setAvatarHealthAfterAttack(defendingAvatar, spell.getDamagePoints());

                setAvatarManna(actingAvatar, spell.getMannaCost());

                autoCorrectPlayerHealth(defendingAvatar);
            }
        }
    }

    public void playerHeal(Avatar actingAvatar, final Spell healSpell) {

        if (actingAvatar.getManna().getRunning() >= healSpell.getMannaCost()) {

            setAvatarHealthAfterHeal(actingAvatar, healSpell.getDamagePoints());

            setAvatarManna(actingAvatar, healSpell.getMannaCost());
        }

        autoCorrectPlayerHealth(actingAvatar);
    }

    public void autoCorrectPlayerHealth(Avatar avatar) {

        if (avatar.getHealth().getRunning() > avatar.getHealth().getActual()) {

            avatar.getHealth().setRunning(avatar.getHealth().getActual());
        }

        if (avatar.getHealth().getRunning() < 0) {

            avatar.getHealth().setRunning(0);
        }
    }

    public void setAvatarManna(Avatar avatar, int mannaCost) {

        avatar.getManna().setRunning(avatar.getManna().getRunning() - mannaCost);

        if (avatar.getManna().getRunning() < 0) {

            avatar.getManna().setRunning(0);
        }
    }

    public void setAvatarHealthAfterAttack(Avatar avatar, int damage) {

        avatar.getHealth().setRunning(avatar.getHealth().getRunning() - damage);
    }

    public void setAvatarHealthAfterHeal(Avatar avatar, int healPoints) {

        avatar.getHealth().setRunning(avatar.getHealth().getRunning() + healPoints);
    }

    public boolean checkIfEventEnded(PvpEvent pvpEvent) {

        return pvpEvent.getPlayer1().getHealth().getRunning() == 0 || pvpEvent.getPlayer2().getHealth().getRunning() == 0;

    }

    public void changeWhosTurn(PvpEvent pvpEvent) {

        if (pvpEvent.getWhosTurn().equals(pvpEvent.getPlayer1().getUsername())) {

            pvpEvent.setWhosTurn(pvpEvent.getPlayer2().getUsername());

        } else {

            pvpEvent.setWhosTurn(pvpEvent.getPlayer1().getUsername());

        }
    }

    private void updatePvpEventInDB(PvpEvent pvpEvent) {

        pvpEventService.updatePvpEvent(pvpEvent);
    }

    private void updateAvatarsInDB(Avatar avatar1, Avatar avatar2) {

        avatarService.updateAvatar(avatar1);

        avatarService.updateAvatar(avatar2);
    }
}