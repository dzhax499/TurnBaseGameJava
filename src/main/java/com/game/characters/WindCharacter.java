package com.game.characters;

import com.game.skills.elemental.AirSlashSkill;
import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;

public class WindCharacter extends BaseCharacter {

    public WindCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Wind: Speedster - High Speed, Low HP & Defense
        super(name, 90, 30, 10, 50);
    }

    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new AirSlashSkill());
    }
}
