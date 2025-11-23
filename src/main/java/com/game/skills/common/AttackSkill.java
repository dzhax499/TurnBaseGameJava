package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;

public class AttackSkill implements Skill {

    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public int getFpCost() {
        return 0; // Serangan biasa gratis
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        System.out.println(user.getName() + " menyerang " + target.getName() + "!");
        int damage = user.getAttackPower();
        target.takeDamageWithMechanics(damage, user);
    }
}
