package com.game.skills.elemental;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.effects.FreezeEffect;

public class IceBlastSkill implements Skill {

    @Override
    public String getName() {
        return "Ice Blast";
    }

    @Override
    public int getFpCost() {
        return 15;
    }

    @Override
    public void use(BaseCharacter user, BaseCharacter target) {
        // Damage = Attack × 1.3
        int damage = (int) (user.getAttackPower() * 1.3);
        
        if (user.useFocusPoints(getFpCost())) {
            System.out.println(user.getName() + " membekukan " + target.getName() + " dengan Ice Blast!");
            target.takeDamageWithMechanics(damage, user);
            
            // Tambahkan efek Freeze (1 giliran)
            target.addEffect(new FreezeEffect(1));
        }
    }
}
