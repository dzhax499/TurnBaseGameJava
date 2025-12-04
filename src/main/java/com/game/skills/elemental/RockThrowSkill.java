package com.game.skills.elemental;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;

/**
 * Skill elemental Earth yang memberikan pure damage tinggi.
 * 
 * <p>
 * Rock Throw adalah skill signature elemen Earth yang fokus pada
 * damage murni dengan multiplier tertinggi
 * {@value Constants#ROCK_THROW_DAMAGE_MULTIPLIER}x
 * tanpa efek status tambahan. Skill ini ideal untuk burst damage.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 15 FP</li>
 * <li>Damage: Attack x 1.6 (tertinggi!)</li>
 * <li>Effect: Tidak ada</li>
 * <li>Target: Musuh</li>
 * <li>Element: Earth</li>
 * </ul>
 * 
 * <p>
 * <strong>Trade-off:</strong> Damage tinggi tetapi tidak ada crowd control
 * atau damage over time.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see com.game.characters.EarthCharacter
 */
public class RockThrowSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Rock Throw"
     */
    @Override
    public String getName() {
        return "Rock Throw";
    }

    /**
     * @return Biaya FP 15
     */
    @Override
    public int getFpCost() {
        return Constants.ROCK_THROW_FP_COST; // 15 FP
    }

    /**
     * Mengeksekusi skill Rock Throw pada target.
     * 
     * <p>
     * Memberikan pure damage dengan multiplier tertinggi 1.6x
     * tanpa efek status tambahan. Cocok untuk finishing blow.
     * 
     * <p>
     * Formula damage: Attack x 1.6
     * 
     * @param user   Karakter yang menggunakan Rock Throw
     * @param target Karakter yang terkena Rock Throw
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Damage = Attack × 1.6 (buffed!)
        int damage = (int) (user.getAttackPower() * Constants.ROCK_THROW_DAMAGE_MULTIPLIER);

        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " melempar batu besar ke " + target.getName() + "!");
            target.takeDamageWithMechanics(damage, user);
        }
    }
}
