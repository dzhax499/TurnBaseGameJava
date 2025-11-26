package com.game.skills.effects;

import com.game.characters.BaseCharacter;

/**
 * Efek Guard yang meningkatkan defense karakter.
 * Setiap Guard skill memberikan +50% defense untuk 2 turn.
 */
public class GuardEffect extends StatusEffect {

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
        
        System.out.println("🛡️  " + target.getName() + " meningkatkan defense sebesar +" + defenseBoost + "!");
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
        System.out.println("⚠️  " + target.getName() + " defense kembali normal!");
    }
}
