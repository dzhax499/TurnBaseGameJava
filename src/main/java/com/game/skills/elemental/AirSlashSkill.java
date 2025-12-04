package com.game.skills.elemental;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;
import java.util.logging.Logger;

/**
 * Skill elemental Wind yang merupakan spam skill dengan FP cost rendah.
 * 
 * <p>
 * Air Slash adalah skill signature elemen Wind yang dirancang untuk
 * spam dengan FP cost rendah {@value Constants#AIR_SLASH_FP_COST} FP
 * dan damage multiplier yang baik
 * {@value Constants#AIR_SLASH_DAMAGE_MULTIPLIER}x.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 12 FP (paling murah untuk elemental skill)</li>
 * <li>Damage: Attack x 1.5</li>
 * <li>Effect: Tidak ada</li>
 * <li>Target: Musuh</li>
 * <li>Element: Wind</li>
 * </ul>
 * 
 * <p>
 * <strong>Strategy:</strong> Skill ini cocok untuk karakter Wind yang
 * memiliki speed tinggi, memungkinkan pressure konsisten dengan spam skill.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see com.game.characters.WindCharacter
 */
public class AirSlashSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Air Slash"
     */
    @Override
    public String getName() {
        return "Air Slash";
    }

    /**
     * @return Biaya FP 12 (REBALANCED - spam skill!)
     */
    @Override
    public int getFpCost() {
        return Constants.AIR_SLASH_FP_COST; // REBALANCED: 12 FP (spam skill!)
    }

    /**
     * Mengeksekusi skill Air Slash pada target.
     * 
     * <p>
     * Memberikan damage dengan multiplier 1.5x dengan FP cost rendah,
     * memungkinkan penggunaan berulang dalam pertarungan.
     * 
     * <p>
     * Formula damage: Attack x 1.5
     * 
     * @param user   Karakter yang menggunakan Air Slash
     * @param target Karakter yang terkena Air Slash
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Damage = Attack × 1.5 (buffed!)
        int damage = (int) (user.getAttackPower() * Constants.AIR_SLASH_DAMAGE_MULTIPLIER);

        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " menyayat " + target.getName() + " dengan angin!");
            target.takeDamageWithMechanics(damage, user);
        }
    }
}
