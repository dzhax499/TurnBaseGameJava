package com.game.skills.effects;

import java.util.logging.Logger;
import com.game.characters.BaseCharacter;

/**
 * Efek status Freeze yang membuat karakter tidak dapat bergerak.
 * 
 * <p>
 * Freeze adalah efek debuff yang mencegah karakter melakukan aksi apapun
 * selama durasi efek berlangsung. Karakter yang terkena freeze akan melewatkan
 * giliran mereka.
 * 
 * <p>
 * Efek ini biasanya diaplikasikan oleh skill elemen Water seperti Ice Blast.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see StatusEffect
 * @see com.game.skills.elemental.IceBlastSkill
 * @see BaseCharacter#canMove()
 */
public class FreezeEffect extends StatusEffect {

    /** Logger untuk mencatat aktivitas efek freeze */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Konstruktor untuk membuat efek Freeze baru.
     * 
     * @param duration Durasi efek dalam jumlah giliran
     */
    public FreezeEffect(int duration) {
        super("Freeze", duration, EffectType.DEBUFF);
    }

    /**
     * Dipanggil saat efek Freeze pertama kali diterapkan ke karakter.
     * Menampilkan pesan bahwa target membeku.
     * 
     * @param target Karakter yang terkena efek Freeze
     */
    @Override
    public void apply(BaseCharacter target) {
        logger.info(target.getName() + " membeku! (Freeze)");
    }

    /**
     * Dipanggil setiap giliran untuk memproses efek Freeze.
     * Freeze tidak memberikan damage, hanya mencegah gerakan.
     * 
     * @param target Karakter yang terkena efek Freeze
     */
    @Override
    public void tick(BaseCharacter target) {
        // Freeze tidak memberikan damage, hanya mencegah gerakan
        logger.info(target.getName() + " masih membeku dan tidak bisa bergerak!");
    }

    /**
     * Dipanggil saat efek Freeze berakhir atau dihapus.
     * Menampilkan pesan bahwa target tidak lagi membeku.
     * 
     * @param target Karakter yang efek Freeze-nya berakhir
     */
    @Override
    public void remove(BaseCharacter target) {
        logger.info(target.getName() + " tidak lagi membeku.");
    }
}
