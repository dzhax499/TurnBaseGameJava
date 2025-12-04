package com.game.skills.elemental;
import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.BurnEffect;
import com.utils.Constants;

public class FireballSkill implements Skill {
    Logger logger = Logger.getLogger(getClass().getName());
    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public int getFpCost() {
        return Constants.FIREBALL_FP_COST; // REBALANCED: 18 FP
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Damage = Attack × 1.4
        int damage = (int) (user.getAttackPower() * Constants.FIREBALL_DAMAGE_MULTIPLIER);

        if (user.useFocusPoints(getFpCost())) {
            logger.info(user.getName() + " menembakkan bola api ke " + target.getName() + "!");
            target.takeDamageWithMechanics(damage, user);

            // Tambahkan efek Burn (3 giliran, 4 damage per turn)
            target.addEffect(new BurnEffect(Constants.BURN_DEFAULT_DURATION, Constants.BURN_DAMAGE_PER_TURN));
        }
    }
}
