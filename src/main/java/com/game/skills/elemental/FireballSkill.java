package com.game.skills.elemental;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.BurnEffect;

public class FireballSkill implements Skill {

    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public int getFpCost() {
        return 15; // BALANCED: Reduced from 20 to 15
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Damage = Attack × 1.5
        int damage = (int) (user.getAttackPower() * 1.5);

        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " menembakkan bola api ke " + target.getName() + "!");
            target.takeDamageWithMechanics(damage, user);

            // Tambahkan efek Burn (3 giliran, 5 damage per turn)
            target.addEffect(new BurnEffect(3, 5));
        }
    }
}
