package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.GuardEffect;
import com.utils.Constants;
import java.util.logging.Logger;

/**
 * Skill pertahanan yang meningkatkan defense karakter.
 * 
 * <p>
 * Guard adalah skill gratis (0 FP) yang memberikan buff defense
 * dengan multiplier {@value Constants#GUARD_DEFENSE_MULTIPLIER}x untuk
 * {@value Constants#GUARD_DEFAULT_DURATION} giliran. Skill ini berguna
 * untuk bertahan dari serangan kuat musuh.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 0 (gratis)</li>
 * <li>Effect: Defense x2.5 untuk 1 turn</li>
 * <li>Target: Diri sendiri</li>
 * <li>Stackable: Tidak (efek baru menimpa yang lama)</li>
 * </ul>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see GuardEffect
 * @see Constants#GUARD_DEFENSE_MULTIPLIER
 */
public class GuardSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Guard"
     */
    @Override
    public String getName() {
        return "Guard";
    }

    /**
     * @return Biaya FP 0 - opsi defensif gratis
     */
    @Override
    public int getFpCost() {
        return Constants.GUARD_SKILL_FP_COST; // 0 FP (free defensive option)
    }

    /**
     * @return true karena Guard menargetkan diri sendiri
     */
    @Override
    public boolean isSelfTargeting() {
        return true;
    }

    /**
     * Mengeksekusi skill Guard pada user.
     * 
     * <p>
     * Menerapkan GuardEffect yang meningkatkan defense user
     * dengan multiplier 2.5x untuk 1 giliran.
     * 
     * @param user   Karakter yang menggunakan Guard
     * @param target Tidak digunakan (skill self-targeting)
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Consistent dengan skill lain - call useFocusPoints meskipun cost 0
        if (user.useFocusPoints(getFpCost())) {
            // Guard memberikan GuardEffect untuk mengurangi damage yang diterima
            logger.info(user.getName() + " bersiap menahan serangan! (Guard)");
            logger.info("   Defense ditingkatkan 150% untuk 1 turn!"); // 2.5x multiplier

            // Tambahkan Guard Effect
            GuardEffect guardEffect = new GuardEffect();
            user.addEffect(guardEffect);
        }
    }
}
