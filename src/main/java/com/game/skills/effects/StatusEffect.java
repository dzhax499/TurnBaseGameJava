package com.game.skills.effects;

import com.game.characters.BaseCharacter;

/**
 * Kelas abstrak untuk semua efek status (Buff/Debuff).
 * 
 * StatusEffect adalah base class untuk semua efek yang dapat diterapkan
 * pada karakter, seperti Burn, Freeze, Guard, Attack Buff, dll.
 * 
 * Setiap efek memiliki:
 * - name: Nama efek
 * - duration: Berapa giliran efek bertahan
 * - type: BUFF atau DEBUFF
 * 
 * Lifecycle efek:
 * 1. apply() - Dipanggil saat efek pertama kali diterapkan
 * 2. tick() - Dipanggil setiap giliran untuk memproses efek
 * 3. decrementDuration() - Mengurangi durasi setiap giliran
 * 4. remove() - Dipanggil saat efek berakhir
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BurnEffect
 * @see FreezeEffect
 * @see GuardEffect
 * @see AttackBuffEffect
 */
public abstract class StatusEffect {

    /** Nama efek */
    private String name;

    /** Durasi efek dalam giliran */
    private int duration;

    /** Tipe efek (BUFF atau DEBUFF) */
    private EffectType type;

    /**
     * Konstruktor untuk membuat status effect baru.
     * 
     * @param name     Nama efek
     * @param duration Durasi dalam giliran
     * @param type     Tipe efek (BUFF atau DEBUFF)
     */
    protected StatusEffect(String name, int duration, EffectType type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }

    /**
     * Dipanggil saat efek pertama kali diterapkan ke karakter.
     * Implementasi harus menjelaskan apa yang terjadi saat efek mulai.
     * 
     * @param target Karakter yang terkena efek
     */
    public abstract void apply(BaseCharacter target);

    /**
     * Dipanggil setiap giliran untuk memproses efek.
     * Implementasi harus menjelaskan apa yang terjadi setiap giliran.
     * Contoh: Burn memberikan damage, Guard meningkatkan defense, dll.
     * 
     * @param target Karakter yang terkena efek
     */
    public abstract void tick(BaseCharacter target);

    /**
     * Dipanggil saat efek berakhir atau dihapus.
     * Implementasi harus membersihkan semua perubahan yang dilakukan efek.
     * Contoh: Guard mengembalikan defense ke nilai semula.
     * 
     * @param target Karakter yang efeknya berakhir
     */
    public abstract void remove(BaseCharacter target);

    /**
     * Mengurangi durasi efek sebanyak 1 giliran.
     * Dipanggil otomatis setiap akhir giliran.
     */
    public void decrementDuration() {
        this.duration--;
    }

    /**
     * @return Nama efek
     */
    public String getName() {
        return name;
    }

    /**
     * @return Durasi tersisa dalam giliran
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return Tipe efek (BUFF atau DEBUFF)
     */
    public EffectType getType() {
        return type;
    }

    /**
     * Mengecek apakah efek sudah expired (durasi habis).
     * 
     * @return true jika durasi <= 0
     */
    public boolean isExpired() {
        return duration <= 0;
    }
}
