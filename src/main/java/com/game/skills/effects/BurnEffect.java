package com.game.skills.effects;

import com.game.characters.BaseCharacter;
import java.util.logging.Logger;

/**
 * Efek Burn: Memberikan damage setiap giliran.
 */
public class BurnEffect extends StatusEffect {
    Logger logger = Logger.getLogger(getClass().getName());
    private int damagePerTurn;

    public BurnEffect(int duration, int damagePerTurn) {
        super("Burn", duration, EffectType.DEBUFF);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void apply(BaseCharacter target) {
        logger.info(target.getName() + " terbakar! (Burn)");
    }

    @Override
    public void tick(BaseCharacter target) {
        target.takeTrueDamage(damagePerTurn);
        String gainBurnStr = target.getName() + " menerima " + damagePerTurn + " damage dari Burn!";
        logger.info(gainBurnStr);
    }

    @Override
    public void remove(BaseCharacter target) {
        logger.info(target.getName() + " tidak lagi terbakar.");
    }
}
