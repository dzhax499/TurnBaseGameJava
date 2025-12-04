package com.game.skills.effects;
import java.util.logging.Logger;
import com.game.characters.BaseCharacter;

/**
 * Efek Freeze: Membuat karakter tidak bisa bergerak selama durasi tertentu.
 */
public class FreezeEffect extends StatusEffect {
    Logger logger = Logger.getLogger(getClass().getName());
    public FreezeEffect(int duration) {
        super("Freeze", duration, EffectType.DEBUFF);
    }

    @Override
    public void apply(BaseCharacter target) {
        logger.info(target.getName() + " membeku! (Freeze)");
    }

    @Override
    public void tick(BaseCharacter target) {
        // Freeze tidak memberikan damage, hanya mencegah gerakan
        logger.info(target.getName() + " masih membeku dan tidak bisa bergerak!");
    }

    @Override
    public void remove(BaseCharacter target) {
        logger.info(target.getName() + " tidak lagi membeku.");
    }
}
