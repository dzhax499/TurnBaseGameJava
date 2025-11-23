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
        return 10; // Biaya FP untuk heal
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Heal menggunakan Attack × 1.5
        int healAmount = (int) (user.getAttackPower() * 1.5);
        
        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " menggunakan Heal pada " + target.getName() + "!");
            target.heal(healAmount);
        }
    }
}
