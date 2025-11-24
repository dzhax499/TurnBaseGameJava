package com.game.skills.effects;

import com.game.characters.BaseCharacter;

/**
 * Efek Attack Buff: Meningkatkan serangan karakter sementara.
 */
public class AttackBuffEffect extends StatusEffect {

    private int attackBonus;

    public AttackBuffEffect(int duration, int attackBonus) {
        super("Attack Buff", duration, EffectType.BUFF);
        this.attackBonus = attackBonus;
    }

    @Override
    public void apply(BaseCharacter target) {
        target.setAttackPower(target.getAttackPower() + attackBonus);
        System.out.println(target.getName() + " mendapat Attack Buff! (+" + attackBonus + " Attack)");
    }

    @Override
    public void tick(BaseCharacter target) {
        // Buff tidak perlu proses setiap giliran, hanya aktif
    }

    @Override
    public void remove(BaseCharacter target) {
        target.setAttackPower(target.getAttackPower() - attackBonus);
        System.out.println(target.getName() + " kehilangan Attack Buff.");
    }
}
