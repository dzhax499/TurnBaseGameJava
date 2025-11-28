package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.utils.Constants;

public class HealSkill implements Skill {

    @Override
    public String getName() {
        return "Heal";
    }

    @Override
    public int getFpCost() {
        return Constants.HEAL_SKILL_FP_COST; // REBALANCED: 12 FP
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // REBALANCED: Fixed heal amount
        int healAmount = Constants.HEAL_SKILL_AMOUNT;

        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " menggunakan Heal pada dirinya sendiri!");
            user.heal(healAmount);
        }
    }
}
