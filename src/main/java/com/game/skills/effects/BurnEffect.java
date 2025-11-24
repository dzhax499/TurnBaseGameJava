package com.game.skills.effects;

import com.game.characters.BaseCharacter;

/**
 * Efek Burn: Memberikan damage setiap giliran.
 */
public class BurnEffect extends StatusEffect {

    private int damagePerTurn;

    public BurnEffect(int duration, int damagePerTurn) {
        super("Burn", duration, EffectType.DEBUFF);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void apply(BaseCharacter target) {
        System.out.println(target.getName() + " terbakar! (Burn)");
    }

    @Override
    public void tick(BaseCharacter target) {
        target.takeDamage(damagePerTurn);
        System.out.println(target.getName() + " menerima " + damagePerTurn + " damage dari Burn!");
    }

    @Override
    public void remove(BaseCharacter target) {
        System.out.println(target.getName() + " tidak lagi terbakar.");
    }
}
