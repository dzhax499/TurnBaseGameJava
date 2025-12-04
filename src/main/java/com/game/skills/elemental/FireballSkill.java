package com.game.skills.elemental;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.BurnEffect;
import com.utils.Constants;

/**
 * Skill elemental Fire yang memberikan damage dan efek Burn.
 * 
 * <p>
 * Fireball adalah skill signature elemen Fire yang memberikan
 * damage tinggi dengan multiplier
 * {@value Constants#FIREBALL_DAMAGE_MULTIPLIER}x
 * dan menerapkan efek Burn yang memberikan damage over time.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 18 FP</li>
 * <li>Damage: Attack x 1.4</li>
 * <li>Effect: Burn (3 turns, 2 damage/turn)</li>
 * <li>Target: Musuh</li>
 * <li>Element: Fire</li>
 * </ul>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see BurnEffect
 * @see com.game.characters.FireCharacter
 */
public class FireballSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Fireball"
     */
    @Override
    public String getName() {
        return "Fireball";
    }

    /**
     * @return Biaya FP 18 (REBALANCED)
     */
    @Override
    public int getFpCost() {
        return Constants.FIREBALL_FP_COST; // REBALANCED: 18 FP
    }

    /**
     * Mengeksekusi skill Fireball pada target.
     * 
     * <p>
     * Memberikan damage dengan multiplier 1.4x dan menerapkan
     * efek Burn yang memberikan 2 true damage per turn selama 3 turn.
     * 
     * <p>
     * Formula damage: Attack x 1.4
     * 
     * @param user   Karakter yang menggunakan Fireball
     * @param target Karakter yang terkena Fireball
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Damage = Attack × 1.4
        int damage = (int) (user.getAttackPower() * Constants.FIREBALL_DAMAGE_MULTIPLIER);

        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " menembakkan bola api ke " + target.getName() + "!");
            target.takeDamageWithMechanics(damage, user);

            // Tambahkan efek Burn (3 giliran, 2 damage per turn)
            target.addEffect(new BurnEffect(Constants.BURN_DEFAULT_DURATION, Constants.BURN_DAMAGE_PER_TURN));
        }
    }
}
