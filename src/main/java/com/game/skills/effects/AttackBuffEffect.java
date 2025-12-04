package com.game.skills.effects;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;

/**
 * Efek status Attack Buff yang meningkatkan kekuatan serangan karakter.
 * 
 * <p>
 * Attack Buff adalah efek buff yang meningkatkan attack power karakter
 * dengan jumlah tertentu selama durasi efek berlangsung. Efek ini membantu
 * karakter memberikan damage lebih besar kepada musuh.
 * 
 * <p>
 * Efek ini dapat diaplikasikan oleh berbagai skill atau item dalam game.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see StatusEffect
 * @see Constants#ATTACK_BUFF_MULTIPLIER
 */
public class AttackBuffEffect extends StatusEffect {

    /** Jumlah attack power yang ditambahkan */
    private int attackBonus;

    /** Logger untuk mencatat aktivitas efek attack buff */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Konstruktor untuk membuat efek Attack Buff baru.
     * 
     * @param duration    Durasi efek dalam jumlah giliran
     * @param attackBonus Jumlah attack power yang ditambahkan
     */
    public AttackBuffEffect(int duration, int attackBonus) {
        super("Attack Buff", duration, EffectType.BUFF);
        this.attackBonus = attackBonus;
    }

    /**
     * Dipanggil saat efek Attack Buff pertama kali diterapkan ke karakter.
     * Meningkatkan attack power karakter dengan jumlah attackBonus.
     * 
     * @param target Karakter yang mendapat efek Attack Buff
     */
    @Override
    public void apply(BaseCharacter target) {
        target.setAttackPower(target.getAttackPower() + attackBonus);
        String gainAttackBuffStr = target.getName() + " mendapat Attack Buff! (+" + attackBonus + " Attack)";
        logger.info(gainAttackBuffStr);
    }

    /**
     * Dipanggil setiap giliran untuk memproses efek Attack Buff.
     * Buff tidak memerlukan processing khusus setiap giliran, hanya tetap aktif.
     * 
     * @param target Karakter yang terkena efek Attack Buff
     */
    @Override
    public void tick(BaseCharacter target) {
        // Buff tidak perlu proses setiap giliran, hanya aktif
    }

    /**
     * Dipanggil saat efek Attack Buff berakhir atau dihapus.
     * Mengembalikan attack power ke nilai semula.
     * 
     * @param target Karakter yang efek Attack Buff-nya berakhir
     */
    @Override
    public void remove(BaseCharacter target) {
        target.setAttackPower(target.getAttackPower() - attackBonus);
        logger.info(target.getName() + " kehilangan Attack Buff.");
    }
}
