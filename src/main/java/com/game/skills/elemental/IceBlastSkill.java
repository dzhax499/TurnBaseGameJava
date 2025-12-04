package com.game.skills.elemental;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.FreezeEffect;
import com.utils.Constants;

/**
 * Skill elemental Water yang memberikan damage dan efek Freeze.
 * 
 * <p>
 * Ice Blast adalah skill signature elemen Water yang memberikan
 * damage dengan multiplier {@value Constants#ICE_BLAST_DAMAGE_MULTIPLIER}x
 * dan menerapkan efek Freeze yang membuat target tidak bisa bergerak.
 * 
 * <p>
 * Karakteristik:
 * <ul>
 * <li>FP Cost: 20 FP (paling mahal)</li>
 * <li>Damage: Attack x 1.3</li>
 * <li>Effect: Freeze (1 turn - target skip turn)</li>
 * <li>Target: Musuh</li>
 * <li>Element: Water</li>
 * </ul>
 * 
 * <p>
 * <strong>Note:</strong> FP cost tinggi karena efek Freeze sangat kuat
 * (membuat musuh kehilangan 1 giliran).
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Skill
 * @see FreezeEffect
 * @see com.game.characters.WaterCharacter
 */
public class IceBlastSkill implements Skill {

    /** Logger untuk mencatat aktivitas skill */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @return Nama skill "Ice Blast"
     */
    @Override
    public String getName() {
        return "Ice Blast";
    }

    /**
     * @return Biaya FP 20 (REBALANCED - paling mahal)
     */
    @Override
    public int getFpCost() {
        return Constants.ICE_BLAST_FP_COST; // REBALANCED: 20 FP
    }

    /**
     * Mengeksekusi skill Ice Blast pada target.
     * 
     * <p>
     * Memberikan damage dengan multiplier 1.3x dan menerapkan
     * efek Freeze yang membuat target tidak bisa bergerak selama 1 turn.
     * 
     * <p>
     * Formula damage: Attack x 1.3
     * 
     * @param user   Karakter yang menggunakan Ice Blast
     * @param target Karakter yang terkena Ice Blast
     */
    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Damage = Attack × 1.3
        int damage = (int) (user.getAttackPower() * Constants.ICE_BLAST_DAMAGE_MULTIPLIER);

        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " membekukan " + target.getName() + " dengan Ice Blast!");
            target.takeDamageWithMechanics(damage, user);

            // Tambahkan efek Freeze (1 giliran)
            target.addEffect(new FreezeEffect(Constants.FREEZE_DEFAULT_DURATION));
        }
    }
}
