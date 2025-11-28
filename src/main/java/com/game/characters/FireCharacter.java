package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.elemental.FireballSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.utils.Constants;

public class FireCharacter extends BaseCharacter {

    public FireCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Fire: High Attack, Balanced Speed, Low Defense
        super(name, Constants.FIRE_MAX_HP, Constants.FIRE_ATTACK,
                Constants.FIRE_DEFENSE, Constants.FIRE_SPEED);
    }

    @Override
    protected void initializeSkills() {
        // 3 Common Skills
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());

        // 1 Unique Skill
        this.addSkill(new FireballSkill());
    }

    @Override
    public ElementType getElementType() {
        return ElementType.FIRE;
    }
}
