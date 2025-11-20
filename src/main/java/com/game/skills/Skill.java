package com.game.skills;

import com.game.characters.BaseCharacter;

/**
 * Interface dasar yang harus diimplementasikan oleh semua skill.
 * Mengimplementasikan prinsip Polimorfisme.
 */
public interface Skill {
    
    String getName();
    int getManaCost();

    /**
     * Metode utama untuk mengeksekusi skill.
     * Polimorfisme terjadi di sini: setiap skill (Attack, Heal, Buff) 
     * akan memiliki implementasi metode use() yang berbeda.
     */
    void use(BaseCharacter user, BaseCharacter target);
}