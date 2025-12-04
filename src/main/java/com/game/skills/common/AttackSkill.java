package com.game.skills.common;
import java.util.logging.Logger;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;

public class AttackSkill implements Skill {
    Logger logger = Logger.getLogger(getClass().getName());
    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public int getFpCost() {
        return Constants.ATTACK_SKILL_FP_COST; // 0 FP - Serangan biasa gratis
    }

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
