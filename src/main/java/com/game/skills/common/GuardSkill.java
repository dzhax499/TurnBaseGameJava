package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.GuardEffect;

public class GuardSkill implements Skill {

    @Override
    public String getName() {
        return "Guard";
    }

    @Override
    public int getFpCost() {
        return 8; // BALANCED: Changed from 0 to 8 to prevent spam
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Guard memberikan GuardEffect untuk mengurangi damage yang diterima
        System.out.println(user.getName() + " bersiap menahan serangan! (Guard)");
        System.out.println("   Defense ditingkatkan 50% untuk 2 turn!");

        // Tambahkan Guard Effect
        GuardEffect guardEffect = new GuardEffect();
        user.addEffect(guardEffect);
    }
}
