package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.dto.AttackPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.AttackPlayerResDto;
import com.purgersmight.purgersmightapp.dto.ForfeitPlayerReqDto;
import com.purgersmight.purgersmightapp.dto.ForfeitPlayerResDto;
import com.purgersmight.purgersmightapp.enums.AttackType;
import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.AbstractBuffAndDebuff;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.PvpEvent;
import com.purgersmight.purgersmightapp.models.Spell;
import com.purgersmight.purgersmightapp.utils.BattleUtils;
import com.purgersmight.purgersmightapp.utils.BuffAndDebuffUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private PlayerBattleReceiptService playerBattleReceiptService;

    @Autowired
    private BattleStatisticsService battleStatisticsService;

    @Autowired
    private BuffAndDebuffUtils buffAndDebuffUtils;

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

        buffAndDebuffUtils.processDebuffs(actingAvatar, defendingAvatar);

        buffAndDebuffUtils.processBuffs(actingAvatar);

        playerAttack(actingAvatar, defendingAvatar, attackPlayerReqDto);

        if (checkIfEventEnded(pvpEvent)) {

            logger.log(Level.INFO, String.format("PvP Event with id %s has now ended", pvpEvent.getEventId()));

            adminAfterEventEnd(pvpEvent);

            return new AttackPlayerResDto(true, getWinner(pvpEvent), pvpEvent);
        }

        buffAndDebuffUtils.getRidOfExpiredBuffsOrDebuffs(actingAvatar.getBuffList());

        buffAndDebuffUtils.getRidOfExpiredBuffsOrDebuffs(actingAvatar.getDebuffList());

        changeWhosTurn(pvpEvent);

        updateEventTimestamp(pvpEvent);//updating the timestamp

        updatePvpEventInDB(pvpEvent);

        updateAvatarsInDB(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());

        return new AttackPlayerResDto(false, null, pvpEvent);
    }

    public String getWinner(PvpEvent pvpEvent) {

        if (pvpEvent.getPlayer1().getHealth().getRunning() == 0) {

            return pvpEvent.getPlayer2().getUsername();
        }

        return pvpEvent.getPlayer1().getUsername();
    }

    private void updateEventTimestamp(PvpEvent pvpEvent) {

        pvpEvent.setTimestamp(new Date().getTime());
    }

    public void playerAttack(Avatar actingAvatar, Avatar defendingAvatar, final AttackPlayerReqDto attackPlayerReqDto) {

        if (!buffAndDebuffUtils.alreadyHas(SpellType.DEBUFF_IMMOBILIZE, actingAvatar.getDebuffList())) {

            if (attackPlayerReqDto.getAttackType().equals(AttackType.MELEE)) {

                playerAttackMelee(actingAvatar, defendingAvatar);

            } else {

                playerAttackSpell(actingAvatar, defendingAvatar, attackPlayerReqDto);
            }

        } else {

            AbstractBuffAndDebuff debuff = buffAndDebuffUtils.getBuffOrDeBuffOfType(SpellType.DEBUFF_IMMOBILIZE, actingAvatar.getDebuffList()).get();

            buffAndDebuffUtils.processUsedBuff(debuff);
        }
    }

    public void playerAttackMelee(Avatar actingAvatar, Avatar defendingAvatar) {

        int defenseBonus = buffAndDebuffUtils.getDefenseBonusFromBuff(defendingAvatar.getBuffList());

        if (defenseBonus != -1) {// -1 means immunity from melee attack

            int weaponDamage = BattleUtils.WeaponManager.getDamagePoints(actingAvatar.getWeapon());

            double fullArmourPoint = BattleUtils.ArmourManager.getFullAmourDefensePoints(defendingAvatar.getBodyArmour());

            double damageReduction = BattleUtils.DamageManager.getDamageReduction(fullArmourPoint + defenseBonus);

            int finalDamage = BattleUtils.DamageManager.getFinalDamage(weaponDamage, damageReduction) + buffAndDebuffUtils.getDamageFromWeaponBuffs(actingAvatar);

            setAvatarHealthAfterAttack(defendingAvatar, finalDamage);

            autoCorrectPlayerHealth(defendingAvatar);

            logger.log(Level.INFO, String.format("%s just attacked (melee) %s", actingAvatar.getUsername(), defendingAvatar.getUsername()));
        }
    }

    public void playerAttackSpell(Avatar actingAvatar, Avatar defendingAvatar, final AttackPlayerReqDto attackPlayerReqDto) {

        Spell spell = actingAvatar.getSpellBook().getSpellList().get(attackPlayerReqDto.getSpellPosition());

        if (spell.getSpellType().equals(SpellType.HEAL)) {

            playerHeal(actingAvatar, spell);
        }

        if (actingAvatar.getManna().getRunning() >= spell.getMannaCost()) {

            if (spell.getSpellType().equals(SpellType.DAMAGE)) {

                setAvatarHealthAfterAttack(defendingAvatar, spell.getDamagePoints());

                setAvatarManna(actingAvatar, spell.getMannaCost());

                autoCorrectPlayerHealth(defendingAvatar);

                logger.log(Level.INFO, String.format("%s just attacked (spell) %s", actingAvatar.getUsername(), defendingAvatar.getUsername()));
            }

            if (spell.getSpellType().equals(SpellType.STEAL_HEALTH)) {

                setAvatarHealthAfterAttack(defendingAvatar, spell.getDamagePoints());

                setAvatarManna(actingAvatar, spell.getMannaCost());

                autoCorrectPlayerHealth(defendingAvatar);

                logger.log(Level.INFO, String.format("%s just stole health from %s", actingAvatar.getUsername(), defendingAvatar.getUsername()));
            }

            if (spell.getSpellType().equals(SpellType.STEAL_MANNA)) {

                stealAvatarManna(actingAvatar, defendingAvatar, spell.getDamagePoints());

                setAvatarManna(actingAvatar, spell.getMannaCost());

                autoCorrectPlayerManna(defendingAvatar);

                logger.log(Level.INFO, String.format("%s just stole manna from %s", actingAvatar.getUsername(), defendingAvatar.getUsername()));
            }

            if (spell.getSpellType().equals(SpellType.DEBUFF_DAMAGE)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.DEBUFF_DAMAGE, defendingAvatar.getDebuffList())) {

                    buffAndDebuffUtils.addDebuff(defendingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.DEBUFF_IMMOBILIZE)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.DEBUFF_IMMOBILIZE, defendingAvatar.getDebuffList())) {

                    buffAndDebuffUtils.addDebuff(defendingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.DEBUFF_STEAL_HEALTH)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.DEBUFF_STEAL_HEALTH, defendingAvatar.getDebuffList())) {

                    buffAndDebuffUtils.addDebuff(defendingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.DEBUFF_STEAL_MANNA)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.DEBUFF_STEAL_MANNA, defendingAvatar.getDebuffList())) {

                    buffAndDebuffUtils.addDebuff(defendingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.BUFF_DAMAGE)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.BUFF_DAMAGE, actingAvatar.getBuffList())) {

                    buffAndDebuffUtils.addBuff(actingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.BUFF_DEFENSE)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.BUFF_DEFENSE, actingAvatar.getBuffList())) {

                    buffAndDebuffUtils.addBuff(actingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.BUFF_HEAL)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.BUFF_HEAL, actingAvatar.getBuffList())) {

                    buffAndDebuffUtils.addBuff(actingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }

            if (spell.getSpellType().equals(SpellType.BUFF_MANNA)) {

                if (!buffAndDebuffUtils.alreadyHas(SpellType.BUFF_MANNA, actingAvatar.getBuffList())) {

                    buffAndDebuffUtils.addBuff(actingAvatar, spell);

                    setAvatarManna(actingAvatar, spell.getMannaCost());
                }
            }
        }
    }

    private void stealAvatarManna(Avatar actingAvatar, Avatar defendingAvatar, final int amount) {

        actingAvatar.getManna().setRunning(actingAvatar.getManna().getRunning() + amount);

        defendingAvatar.getManna().setRunning(defendingAvatar.getManna().getRunning() - amount);
    }

    public void playerHeal(Avatar actingAvatar, final Spell healSpell) {

        if (actingAvatar.getManna().getRunning() >= healSpell.getMannaCost()) {

            setAvatarHealthAfterHeal(actingAvatar, healSpell.getDamagePoints());

            setAvatarManna(actingAvatar, healSpell.getMannaCost());

            logger.log(Level.INFO, String.format("%s just healed themselves", actingAvatar.getUsername()));
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

    public void autoCorrectPlayerManna(Avatar avatar) {

        if (avatar.getManna().getRunning() > avatar.getManna().getActual()) {

            avatar.getManna().setRunning(avatar.getManna().getActual());
        }

        if (avatar.getManna().getRunning() < 0) {

            avatar.getManna().setRunning(0);
        }
    }

    public void setAvatarManna(Avatar avatar, final int mannaCost) {

        avatar.getManna().setRunning(avatar.getManna().getRunning() - mannaCost);

        if (avatar.getManna().getRunning() < 0) {

            avatar.getManna().setRunning(0);
        }
    }

    public void setAvatarHealthAfterAttack(Avatar avatar, final int damage) {

        avatar.getHealth().setRunning(avatar.getHealth().getRunning() - damage);
    }

    public void setAvatarHealthAfterHeal(Avatar avatar, final int healPoints) {

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

    private void updatePvpEventInDB(final PvpEvent pvpEvent) {

        pvpEventService.updatePvpEvent(pvpEvent);
    }

    private void updateAvatarsInDB(Avatar avatar1, Avatar avatar2) {

        avatarService.updateAvatar(avatar1);

        avatarService.updateAvatar(avatar2);
    }

    public ForfeitPlayerResDto processForfeitReq(ForfeitPlayerReqDto forfeitPlayerReqDto) {

        ForfeitPlayerResDto forfeitPlayerResDto = new ForfeitPlayerResDto();

        PvpEvent pvpEvent = pvpEventService.getPvpEventByEventId(forfeitPlayerReqDto.getEventId());

        pvpEvent.setEnded(true);

        forfeitPlayerResDto.setEnded(true);

        forfeitPlayerResDto.setPvpEvent(pvpEvent);

        if (forfeitPlayerReqDto.getUsername().equals(pvpEvent.getPlayer1().getUsername())) {

            pvpEvent.getPlayer1().getHealth().setRunning(0);

            forfeitPlayerResDto.setWinner(pvpEvent.getPlayer2().getUsername());

        } else {

            pvpEvent.getPlayer2().getHealth().setRunning(0);

            forfeitPlayerResDto.setWinner(pvpEvent.getPlayer1().getUsername());
        }

        logger.log(Level.INFO,
                String.format("%s has forfeit the battle thus Pvp Event with id %s has now ended",
                        forfeitPlayerReqDto.getUsername(), pvpEvent.getEventId()));

        adminAfterEventEnd(pvpEvent);

        return forfeitPlayerResDto;
    }

    private void adminAfterEventEnd(PvpEvent pvpEvent) {

        awardService.awardWinningPlayer(pvpEvent);

        pvpEventService.removePvpEventById(pvpEvent.getEventId());

        pvpEventService.resetPlayersPvpEventStatus(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());

        pvpEvent.getPlayer1().getHealth().setRunning(pvpEvent.getPlayer1().getHealth().getActual());

        pvpEvent.getPlayer2().getHealth().setRunning(pvpEvent.getPlayer2().getHealth().getActual());

        pvpEvent.getPlayer1().getManna().setRunning(pvpEvent.getPlayer1().getManna().getActual());

        pvpEvent.getPlayer2().getManna().setRunning(pvpEvent.getPlayer2().getManna().getActual());

        playerBattleReceiptService.createBattleReceipt(pvpEvent);

        battleStatisticsService.updateBattleStatistics(pvpEvent);

        updateAvatarsInDB(pvpEvent.getPlayer1(), pvpEvent.getPlayer2());
    }
}
