package com.game.skills.elemental;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;
import java.util.logging.Logger;
public class AirSlashSkill implements Skill {
    Logger logger = Logger.getLogger(getClass().getName());
    @Override
    public String getName() {
        return "Air Slash";
    }

    @Override
    public int getFpCost() {
        return Constants.AIR_SLASH_FP_COST; // REBALANCED: 12 FP (spam skill!)
    }

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
