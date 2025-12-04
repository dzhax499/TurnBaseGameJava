package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import java.util.logging.Logger;
import com.utils.Constants;

/**
 * Skill penyembuhan yang memulihkan HP karakter.
 * 
 * <p>
 * Heal adalah skill yang memulihkan HP user sebesar
 * {@value Constants#HEAL_SKILL_AMOUNT} dengan biaya
 * {@value Constants#HEAL_SKILL_FP_COST} FP. Skill ini penting
 * untuk strategi survival dalam pertarungan panjang.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 12 FP</li>
 * <li>Heal Amount: 35 HP (fixed)</li>
 * <li>Target: Diri sendiri</li>
 * <li>Tidak dapat melebihi max HP</li>
 * </ul>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see BaseCharacter#heal(int)
 * @see Constants#HEAL_SKILL_AMOUNT
 */
public class HealSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Heal"
     */
    @Override
    public String getName() {
        return "Heal";
    }

    /**
     * @return Biaya FP 12 (REBALANCED)
     */
    @Override
    public int getFpCost() {
        return Constants.HEAL_SKILL_FP_COST; // REBALANCED: 12 FP
    }

    /**
     * @return true karena Heal menargetkan diri sendiri
     */
    @Override
    public boolean isSelfTargeting() {
        return true;
    }

    /**
     * Mengeksekusi skill Heal pada user.
     * 
     * <p>
     * Memulihkan HP user sebesar HEAL_SKILL_AMOUNT (35 HP).
     * HP tidak akan melebihi maxHealthPoints.
     * 
     * @param user   Karakter yang menggunakan Heal
     * @param target Tidak digunakan (skill self-targeting)
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Fixed heal amount
        int healAmount = Constants.HEAL_SKILL_AMOUNT;

        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " menggunakan Heal pada dirinya sendiri!");
            user.heal(healAmount);
        }
    }
}
