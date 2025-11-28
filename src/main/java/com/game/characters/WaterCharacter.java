package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.game.skills.elemental.IceBlastSkill;
import com.utils.Constants;

public class WaterCharacter extends BaseCharacter {

    public WaterCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Water: Balanced, Good Defense
        super(name, Constants.WATER_MAX_HP, Constants.WATER_ATTACK,
                Constants.WATER_DEFENSE, Constants.WATER_SPEED);
    }

    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new IceBlastSkill());
    }

    @Override
    public ElementType getElementType() {
        return ElementType.WATER;
    }
}
