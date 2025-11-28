package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;

public class AttackSkill implements Skill {

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
            System.out.println(user.getName() + " menyerang " + target.getName() + "!");
            int damage = user.getAttackPower();
            target.takeDamageWithMechanics(damage, user);
        }
    }
}
