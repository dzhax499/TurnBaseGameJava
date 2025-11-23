package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.game.skills.elemental.IceBlastSkill;

public class WaterCharacter extends BaseCharacter {

    public WaterCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Water: Balanced, Good Defense
        super(name, 120, 25, 25, 10);
    }

    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new IceBlastSkill());
    }
}
