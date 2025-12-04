package com.game.skills.effects;

import com.game.characters.BaseCharacter;
import java.util.logging.Logger;

/**
 * Efek Guard yang meningkatkan defense karakter.
 * Setiap Guard skill memberikan +50% defense untuk 2 turn.
 */
public class GuardEffect extends StatusEffect {

    Logger logger = Logger.getLogger(getClass().getName());
    private int defenseBoost; // Berapa defense yang ditambahkan

    public GuardEffect() {
        super("Guard", 2, EffectType.BUFF);
        this.defenseBoost = 0; // Akan dihitung saat apply
    }

    @Override
    public void apply(BaseCharacter target) {
        // Hitung defense boost: 50% dari current defense
        this.defenseBoost = (int) (target.getDefense() * 0.5);
        
        // Tingkatkan defense target
        target.setDefense(target.getDefense() + defenseBoost);
        String targetName = "🛡️  " + target.getName() + " meningkatkan defense sebesar +" + defenseBoost + "!";
        logger.info(targetName);
    }

    @Override
    public void tick(BaseCharacter target) {
        // Guard effect tidak perlu processing khusus setiap turn
        // Hanya perlu track durasi
    }

    @Override
    public void remove(BaseCharacter target) {
        // Hapus defense boost saat effect berakhir
        target.setDefense(target.getDefense() - defenseBoost);
        logger.info("⚠️  " + target.getName() + " defense kembali normal!");
    }
}
