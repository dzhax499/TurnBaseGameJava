package com.game.skills.common;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;

/**
 * Skill serangan dasar yang tersedia untuk semua karakter.
 * 
 * <p>
 * Basic Attack adalah skill gratis (0 FP) yang memberikan damage
 * berdasarkan attack power user. Skill ini menggunakan semua mekanik
 * combat termasuk dodge, critical hit, dan elemental advantage.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 0 (gratis)</li>
 * <li>Damage: Sama dengan attack power user</li>
 * <li>Target: Musuh</li>
 * <li>Mekanik: Dodge, Crit, Elemental</li>
 * </ul>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see BaseCharacter#takeDamageWithMechanics(int, BaseCharacter)
 */
public class AttackSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Basic Attack"
     */
    @Override
    public String getName() {
        return "Basic Attack";
    }

    /**
     * @return Biaya FP 0 - serangan dasar gratis
     */
    @Override
    public int getFpCost() {
        return Constants.ATTACK_SKILL_FP_COST; // 0 FP - Serangan biasa gratis
    }

    /**
     * Mengeksekusi serangan dasar pada target.
     * 
     * <p>
     * Memberikan damage sebesar attack power user dengan semua
     * mekanik combat (dodge, crit, elemental advantage).
     * 
     * @param user   Karakter yang menyerang
     * @param target Karakter yang diserang
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Consistent dengan skill lain - call useFocusPoints meskipun cost 0
        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " menyerang " + target.getName() + "!");
            int damage = user.getAttackPower();
            target.takeDamageWithMechanics(damage, user);
        }
    }
}
