package com.game.skills.elemental;
import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.FreezeEffect;
import com.utils.Constants;

public class IceBlastSkill implements Skill {
    Logger logger = Logger.getLogger(getClass().getName());
    @Override
    public String getName() {
        return "Ice Blast";
    }

    @Override
    public int getFpCost() {
        return Constants.ICE_BLAST_FP_COST; // REBALANCED: 20 FP
    }

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
