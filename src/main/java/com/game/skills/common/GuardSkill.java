package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.skills.effects.GuardEffect;
import com.utils.Constants;

public class GuardSkill implements Skill {

    @Override
    public String getName() {
        return "Guard";
    }

    @Override
    public int getFpCost() {
        return Constants.GUARD_SKILL_FP_COST; // 0 FP (free defensive option)
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Consistent dengan skill lain - call useFocusPoints meskipun cost 0
        if (user.useFocusPoints(getFpCost())) {
            // Guard memberikan GuardEffect untuk mengurangi damage yang diterima
            System.out.println(user.getName() + " bersiap menahan serangan! (Guard)");
            System.out.println("   Defense ditingkatkan 150% untuk 1 turn!"); // 2.5x multiplier

            // Tambahkan Guard Effect
            GuardEffect guardEffect = new GuardEffect();
            user.addEffect(guardEffect);
        }
    }
}
