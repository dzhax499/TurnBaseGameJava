package com.game.effects;

import com.game.characters.BaseCharacter;

/**
 * Kelas abstrak untuk semua efek status (Buff/Debuff).
 */
public abstract class StatusEffect {
    
    private String name;
    private int duration; // Dalam giliran
    private EffectType type;
    
    public StatusEffect(String name, int duration, EffectType type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }
    
    /**
     * Dipanggil saat efek pertama kali diterapkan ke karakter.
     */
    public abstract void apply(BaseCharacter target);
    
    /**
     * Dipanggil setiap giliran untuk memproses efek.
     */
    public abstract void tick(BaseCharacter target);
    
    /**
     * Dipanggil saat efek berakhir atau dihapus.
     */
    public abstract void remove(BaseCharacter target);
    
    /**
     * Mengurangi durasi efek. Return true jika efek masih aktif.
     */
    public boolean decreaseDuration() {
        this.duration--;
        return this.duration > 0;
    }
    
    // Getters
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public EffectType getType() { return type; }
    
    public boolean isExpired() {
        return duration <= 0;
    }
}
