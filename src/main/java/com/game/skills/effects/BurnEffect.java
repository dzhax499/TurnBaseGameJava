package com.game.skills.effects;

import com.game.characters.BaseCharacter;import import java.util.logging.Logger;

/**
 * Efek status Burn yang memberikan damage over time (DoT).
 * 
 * Burn adalah efek debuff yang memberikan true damage kepada target
 * setiap giliran. True damage mengabaikan defense dan langsung mengurangi HP.
 * Efek ini biasanya diaplikasikan oleh skill elemen Fire seperti Fireball.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see StatusEffect
 * @see com.game.skills.elemental.FireballSkill
 */
public class BurnEffect extends StatusEffect {

    /** Logger untuk mencatat aktivitas efek burn */
    Logger logger = Logger.getLogger(getClass().getName());

    /** Jumlah damage yang diberikan setiap giliran */
    private int damagePerTurn;

    /**
     * Konstruktor untuk membuat efek Burn baru.
     * 
     * @param duration      Durasi efek dalam jumlah giliran
     * @param damagePerTurn Jumlah true damage yang diberikan setiap giliran
     */
    public BurnEffect(int duration, int damagePerTurn) {
        super("Burn", duration, EffectType.DEBUFF);
        this.damagePerTurn = damagePerTurn;
    }

    /**
     * Dipanggil saat efek Burn pertama kali diterapkan ke karakter.
     * Menampilkan pesan bahwa target terbakar.
     * 
     * @param target Karakter yang terkena efek Burn
     */
    @Override
    public void apply(BaseCharacter target) {
        logger.info(target.getName() + " terbakar! (Burn)");
    }

    /**
     * Dipanggil setiap giliran untuk memproses efek Burn.
     * Memberikan true damage kepada target yang mengabaikan defense.
     * 
     * @param target Karakter yang terkena efek Burn
     */
    @Override
    public void tick(BaseCharacter target) {
        target.takeTrueDamage(damagePerTurn);
        String gainBurnStr = target.getName() + " menerima " + damagePerTurn + " damage dari Burn!";
        logger.info(gainBurnStr);
    }

    /**
     * Dipanggil saat efek Burn berakhir atau dihapus.
     * Menampilkan pesan bahwa target tidak lagi terbakar.
     * 
     * @param target Karakter yang efek Burn-nya berakhir
     */
    @Override
    public void remove(BaseCharacter target) {
        logger.info(target.getName() + " tidak lagi terbakar.");
    }
}
