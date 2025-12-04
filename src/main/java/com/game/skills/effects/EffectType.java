package com.game.skills.effects;

/**
 * Enum untuk tipe efek status dalam game.
 * 
 * <p>
 * Tipe efek menentukan apakah efek tersebut menguntungkan (BUFF)
 * atau merugikan (DEBUFF) bagi karakter yang terkena.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see StatusEffect
 */
public enum EffectType {
    /** Efek positif yang menguntungkan karakter (contoh: Guard, Attack Buff) */
    BUFF,

    /** Efek negatif yang merugikan karakter (contoh: Burn, Freeze) */
    DEBUFF
}
