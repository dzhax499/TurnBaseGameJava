package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.game.skills.elemental.RockThrowSkill;

public class EarthCharacter extends BaseCharacter {

    public EarthCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Earth: Tank - High HP & Defense, Low Speed
        super(name, 140, 25, 30, 5);
    }

    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new RockThrowSkill());
    }

    @Override
    public ElementType getElementType() {
        return ElementType.EARTH;
    }
}
