package com.game.skills.elemental;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;

public class AirSlashSkill implements Skill {

    @Override
    public String getName() {
        return "Air Slash";
    }

    @Override
    public int getFpCost() {
        return 15; // BALANCED: Reduced from 20 to 15
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Damage = Attack × 1.2
        int damage = (int) (user.getAttackPower() * 1.2);

        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " menyayat " + target.getName() + " dengan angin!");
            target.takeDamageWithMechanics(damage, user);
        }
    }
}
