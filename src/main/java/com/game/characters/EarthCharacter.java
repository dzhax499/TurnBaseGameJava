package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.game.skills.elemental.RockThrowSkill;
import com.utils.Constants;

public class EarthCharacter extends BaseCharacter {

    public EarthCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Earth: Tank - High HP & Defense, Low Speed
        super(name, Constants.EARTH_MAX_HP, Constants.EARTH_ATTACK,
                Constants.EARTH_DEFENSE, Constants.EARTH_SPEED);
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
