package com.game.skills.elemental;
import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;

public class RockThrowSkill implements Skill {
    Logger logger = Logger.getLogger(getClass().getName());
    @Override
    public String getName() {
        return "Rock Throw";
    }

    @Override
    public int getFpCost() {
        return Constants.ROCK_THROW_FP_COST; // 15 FP
    }

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
