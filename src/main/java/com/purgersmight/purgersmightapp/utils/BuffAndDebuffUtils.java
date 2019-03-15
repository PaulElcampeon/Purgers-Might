package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.enums.SpellType;
import com.purgersmight.purgersmightapp.models.*;
import com.purgersmight.purgersmightapp.services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Scope("singleton")
public class BuffAndDebuffUtils {

    @Autowired
    private BattleService battleService;

//    @Autowired
//    public BuffAndDebuffUtils(BattleService battleService) {
//
//        this.battleService = battleService;
//    }

    public void processBuffs(Avatar actingAvatar) {

        processBuffHeal(actingAvatar);

        processBuffManna(actingAvatar);
    }

    private void processBuffManna(Avatar actingAvatar) {

        if (alreadyHas(SpellType.BUFF_MANNA, actingAvatar.getBuffList())) {

            AbstractBuffAndDebuff buff = getBuffOrDeBuffOfType(SpellType.BUFF_MANNA, actingAvatar.getBuffList()).get();

            actingAvatar.getManna().setRunning(actingAvatar.getManna().getRunning() + buff.getAmount());

            battleService.autoCorrectPlayerManna(actingAvatar);

            processUsedBuff(buff);
        }
    }

    private void processBuffHeal(Avatar actingAvatar) {

        if (alreadyHas(SpellType.BUFF_HEAL, actingAvatar.getBuffList())) {

            AbstractBuffAndDebuff buff = getBuffOrDeBuffOfType(SpellType.BUFF_HEAL, actingAvatar.getBuffList()).get();

            actingAvatar.getHealth().setRunning(actingAvatar.getHealth().getRunning() + buff.getAmount());

            battleService.autoCorrectPlayerHealth(actingAvatar);

            processUsedBuff(buff);
        }
    }

    public void processDebuffs(Avatar actingAvatar, Avatar defendingAvatar) {

        processDebuffDamage(actingAvatar);

        processDebuffStealHealth(actingAvatar, defendingAvatar);

        processDebuffStealManna(actingAvatar, defendingAvatar);
    }

    private void processDebuffStealManna(Avatar actingAvatar, Avatar defendingAvatar) {

        if (alreadyHas(SpellType.DEBUFF_STEAL_MANNA, actingAvatar.getDebuffList())) {

            AbstractBuffAndDebuff debuff = getBuffOrDeBuffOfType(SpellType.DEBUFF_STEAL_MANNA, actingAvatar.getDebuffList()).get();

            if (actingAvatar.getManna().getRunning() >= debuff.getAmount()) {

                actingAvatar.getManna().setRunning(actingAvatar.getManna().getRunning() - debuff.getAmount());

                defendingAvatar.getManna().setRunning(defendingAvatar.getManna().getRunning() + debuff.getAmount());

            } else if (actingAvatar.getManna().getRunning() > 0 && actingAvatar.getManna().getRunning() < debuff.getAmount()) {

                defendingAvatar.getManna().setRunning(defendingAvatar.getManna().getRunning() + actingAvatar.getManna().getRunning());

                actingAvatar.getManna().setRunning(0);
            }

            battleService.autoCorrectPlayerManna(actingAvatar);

            battleService.autoCorrectPlayerManna(defendingAvatar);

            processUsedBuff(debuff);
        }
    }

    private void processDebuffStealHealth(Avatar actingAvatar, Avatar defendingAvatar) {

        if (alreadyHas(SpellType.DEBUFF_STEAL_HEALTH, actingAvatar.getDebuffList())) {

            AbstractBuffAndDebuff debuff = getBuffOrDeBuffOfType(SpellType.DEBUFF_STEAL_HEALTH, actingAvatar.getDebuffList()).get();

            if (actingAvatar.getHealth().getRunning() >= debuff.getAmount()) {

                actingAvatar.getHealth().setRunning(actingAvatar.getHealth().getRunning() - debuff.getAmount());

                defendingAvatar.getHealth().setRunning(defendingAvatar.getHealth().getRunning() + debuff.getAmount());

            } else if (actingAvatar.getHealth().getRunning() > 0 && actingAvatar.getHealth().getRunning() < debuff.getAmount()) {

                defendingAvatar.getHealth().setRunning(defendingAvatar.getHealth().getRunning() + actingAvatar.getHealth().getRunning());

                actingAvatar.getHealth().setRunning(0);
            }

            battleService.autoCorrectPlayerHealth(actingAvatar);

            battleService.autoCorrectPlayerHealth(defendingAvatar);

            processUsedBuff(debuff);
        }
    }

    private void processDebuffDamage(Avatar actingAvatar) {

        if (alreadyHas(SpellType.DEBUFF_DAMAGE, actingAvatar.getDebuffList())) {

            AbstractBuffAndDebuff debuff = getBuffOrDeBuffOfType(SpellType.DEBUFF_DAMAGE, actingAvatar.getDebuffList()).get();

            actingAvatar.getHealth().setRunning(actingAvatar.getHealth().getRunning() - debuff.getAmount());

            battleService.autoCorrectPlayerHealth(actingAvatar);

            processUsedBuff(debuff);
        }
    }

    public void processUsedBuff(AbstractBuffAndDebuff debuff) {

        debuff.setNoOfTurns(debuff.getNoOfTurns() - 1);
    }

    public Optional<AbstractBuffAndDebuff> getBuffOrDeBuffOfType(SpellType spellType, List<AbstractBuffAndDebuff> buffList) {

        for (AbstractBuffAndDebuff buff : buffList) {

            if (buff.getSpellType().equals(spellType)) {

                return Optional.of(buff);
            }
        }

        return Optional.empty();
    }

    public int getDefenseBonusFromBuff(List<AbstractBuffAndDebuff> buffList) {

        int bonus = 0;

        for (AbstractBuffAndDebuff buff : buffList) {

            if (buff.getSpellType().equals(SpellType.BUFF_DEFENSE)) {

                bonus = buff.getAmount();

                buff.setNoOfTurns(buff.getNoOfTurns() - 1);

                return bonus;
            }
        }

        return bonus;
    }

    public boolean alreadyHas(SpellType spellType, List<AbstractBuffAndDebuff> abstractBuffOrDebuffs) {

        for (AbstractBuffAndDebuff buff : abstractBuffOrDebuffs) {

            if (buff.getSpellType().equals(spellType)) {

                return true;
            }
        }

        return false;
    }

    public int getDamageFromWeaponBuffs(Avatar actingAvatar) {

        int buffDamage = 0;

        for (AbstractBuffAndDebuff buff : actingAvatar.getBuffList()) {

            if (buff.getSpellType().equals(SpellType.BUFF_DAMAGE)) {

                buffDamage += buff.getAmount();

                buff.setNoOfTurns(buff.getNoOfTurns() - 1);

                return buffDamage;
            }
        }

        return buffDamage;
    }

    public void getRidOfExpiredBuffsOrDebuffs(List<AbstractBuffAndDebuff> buffOrDebuffList) {

        List<AbstractBuffAndDebuff> tempList = new ArrayList<>();

        if (buffOrDebuffList.size() > 0) {

            for (AbstractBuffAndDebuff buff : buffOrDebuffList) {

                if (buff.getNoOfTurns() == 0) {

                    tempList.add(buff);
                }
            }

            for (AbstractBuffAndDebuff buff : tempList) {

                buffOrDebuffList.remove(buff);
            }
        }
    }

    public void addDebuff(Avatar defendingAvatar, final Spell spell) {

        defendingAvatar.getDebuffList().add(new Debuff(spell));
    }

    public void addBuff(Avatar actingAvatar, final Spell spell) {

        actingAvatar.getBuffList().add(new Buff(spell));
    }
}
