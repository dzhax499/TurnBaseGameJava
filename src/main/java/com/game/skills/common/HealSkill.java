package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;

public class HealSkill implements Skill {

    @Override
    public String getName() {
        return "Heal";
    }

    @Override
    public int getFpCost() {
        return 12; // BALANCED: Adjusted from 10 to 12
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Heal menggunakan Attack × 0.8 + 10 (lebih balanced, tidak terlalu besar)
        int healAmount = (int) (user.getAttackPower() * 0.8) + 10;

        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " menggunakan Heal pada dirinya sendiri!");
            user.heal(healAmount);
        }
    }
}
