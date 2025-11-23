package com.game.skills.common;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;

public class GuardSkill implements Skill {

    @Override
    public String getName() {
        return "Guard";
    }

    @Override
    public int getFpCost() {
        return 0;
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Guard biasanya meningkatkan defense sementara atau skip turn dengan mitigasi damage.
        // Karena sistem buff belum kompleks, kita simulasikan dengan pesan saja atau regenerasi FP sedikit.
        System.out.println(user.getName() + " bersiap menahan serangan! (Guard)");
        user.regenFocusPoints(5); // Bonus regen FP saat guard
    }
}
