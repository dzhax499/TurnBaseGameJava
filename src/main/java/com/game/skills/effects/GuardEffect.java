package com.game.skills.effects;

import com.game.characters.BaseCharacter;
import com.utils.Constants;
import java.util.logging.Logger;

/**
 * Efek status Guard yang meningkatkan pertahanan karakter.
 * 
 * <p>
 * Guard adalah efek buff yang meningkatkan defense karakter dengan
 * multiplier {@value Constants#GUARD_DEFENSE_MULTIPLIER}x dari defense saat
 * ini.
 * Efek ini membantu karakter bertahan dari serangan musuh.
 * 
 * <p>
 * Efek ini diaplikasikan oleh skill Guard yang tersedia untuk semua karakter.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see StatusEffect
 * @see com.game.skills.common.GuardSkill
 * @see Constants#GUARD_DEFENSE_MULTIPLIER
 */
public class GuardEffect extends StatusEffect {

    /** Logger untuk mencatat aktivitas efek guard */
    Logger logger = Logger.getLogger(getClass().getName());

    /** Jumlah defense yang ditambahkan saat efek aktif */
    private int defenseBoost;

    /**
     * Konstruktor untuk membuat efek Guard baru.
     * Defense boost akan dihitung saat efek diterapkan.
     */
    public GuardEffect() {
        super("Guard", Constants.GUARD_DEFAULT_DURATION, EffectType.BUFF);
        this.defenseBoost = 0; // Akan dihitung saat apply
    }

    /**
     * Dipanggil saat efek Guard pertama kali diterapkan ke karakter.
     * Menghitung dan menerapkan peningkatan defense.
     * 
     * <p>
     * Formula: defenseBoost = currentDefense * (GUARD_DEFENSE_MULTIPLIER - 1)
     * 
     * @param target Karakter yang mendapat efek Guard
     */
    @Override
    public void apply(BaseCharacter target) {
        // Hitung defense boost berdasarkan multiplier dari Constants
        this.defenseBoost = (int) (target.getDefense() * (Constants.GUARD_DEFENSE_MULTIPLIER - 1));

        // Tingkatkan defense target
        target.setDefense(target.getDefense() + defenseBoost);
        String targetName = "🛡️  " + target.getName() + " meningkatkan defense sebesar +" + defenseBoost + "!";
        logger.info(targetName);
    }

    /**
     * Dipanggil setiap giliran untuk memproses efek Guard.
     * Guard effect tidak memerlukan processing khusus setiap turn,
     * hanya perlu tracking durasi.
     * 
     * @param target Karakter yang terkena efek Guard
     */
    @Override
    public void tick(BaseCharacter target) {
        // Guard effect tidak perlu processing khusus setiap turn
        // Hanya perlu track durasi
    }

    /**
     * Dipanggil saat efek Guard berakhir atau dihapus.
     * Mengembalikan defense ke nilai semula.
     * 
     * @param target Karakter yang efek Guard-nya berakhir
     */
    @Override
    public void remove(BaseCharacter target) {
        // Hapus defense boost saat effect berakhir
        target.setDefense(target.getDefense() - defenseBoost);
        logger.info("⚠️  " + target.getName() + " defense kembali normal!");
    }
}
