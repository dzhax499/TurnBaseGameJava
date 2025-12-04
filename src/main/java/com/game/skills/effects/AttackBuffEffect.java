package com.game.skills.effects;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;

/**
 * Efek Attack Buff: Meningkatkan serangan karakter sementara.
 */
public class AttackBuffEffect extends StatusEffect {

    private int attackBonus;
    Logger logger = Logger.getLogger(getClass().getName());
    public AttackBuffEffect(int duration, int attackBonus) {
        super("Attack Buff", duration, EffectType.BUFF);
        this.attackBonus = attackBonus;
    }

    @Override
    public void apply(BaseCharacter target) {
        target.setAttackPower(target.getAttackPower() + attackBonus);
        String gainAttackBuffStr = target.getName() + " mendapat Attack Buff! (+" + attackBonus + " Attack)"; 
        logger.info(gainAttackBuffStr);
    }

    @Override
    public void tick(BaseCharacter target) {
        // Buff tidak perlu proses setiap giliran, hanya aktif
    }

    @Override
    public void remove(BaseCharacter target) {
        target.setAttackPower(target.getAttackPower() - attackBonus);
        logger.info(target.getName() + " kehilangan Attack Buff.");
    }
}
