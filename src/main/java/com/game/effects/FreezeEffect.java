package com.game.effects;

import com.game.characters.BaseCharacter;

/**
 * Efek Freeze: Membuat karakter tidak bisa bergerak selama durasi tertentu.
 */
public class FreezeEffect extends StatusEffect {
    
    public FreezeEffect(int duration) {
        super("Freeze", duration, EffectType.DEBUFF);
    }
    
    @Override
    public void apply(BaseCharacter target) {
        System.out.println(target.getName() + " membeku! (Freeze)");
    }
    
    @Override
    public void tick(BaseCharacter target) {
        // Freeze tidak memberikan damage, hanya mencegah gerakan
        System.out.println(target.getName() + " masih membeku dan tidak bisa bergerak!");
    }
    
    @Override
    public void remove(BaseCharacter target) {
        System.out.println(target.getName() + " tidak lagi membeku.");
    }
}
