package com.game.characters;

import com.game.skills.elemental.AirSlashSkill;
import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.utils.Constants;

public class WindCharacter extends BaseCharacter {

    public WindCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Wind: Speedster - High Speed, Low HP & Defense
        super(name, Constants.WIND_MAX_HP, Constants.WIND_ATTACK,
                Constants.WIND_DEFENSE, Constants.WIND_SPEED);
    }

    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new AirSlashSkill());
    }

    @Override
    public ElementType getElementType() {
        return ElementType.WIND;
    }
}
