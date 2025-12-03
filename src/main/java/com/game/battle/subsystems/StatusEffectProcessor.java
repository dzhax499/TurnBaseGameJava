package com.game.battle.subsystems;

import com.game.characters.BaseCharacter;

/**
 * Subsystem untuk memproses status effects.
 * Bagian dari Facade Pattern yang digunakan oleh Battle.java.
 */
public class StatusEffectProcessor {

    /**
     * Memproses efek di AWAL giliran (DoT, Stat changes).
     */
    public void processStartTurnEffects(BaseCharacter player) {
        player.applyStartTurnEffects();
    }

    /**
     * Memproses efek di AKHIR giliran (Duration decrement, Removal).
     */
    public void processEndTurnEffects(BaseCharacter player) {
        player.applyEndTurnEffects();
    }
}
