package com.game.skills.elemental;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;

public class RockThrowSkill implements Skill {

    @Override
    public String getName() {
        return "Rock Throw";
    }

    @Override
    public int getFpCost() {
        return 15; // BALANCED: Reduced from 20 to 15
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Damage = Attack × 1.4
        int damage = (int) (user.getAttackPower() * 1.4);

        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " melempar batu besar ke " + target.getName() + "!");
            target.takeDamageWithMechanics(damage, user);
        }
    }
}
