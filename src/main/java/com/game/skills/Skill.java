package com.game.skills;

import com.game.characters.BaseCharacter;

/**
 * Interface dasar untuk semua skill dalam game.
 * 
 * <p>
 * Interface ini mengimplementasikan prinsip Polimorfisme, memungkinkan
 * berbagai jenis skill (Attack, Heal, Buff, Elemental) untuk diperlakukan
 * secara seragam melalui kontrak yang sama.
 * 
 * <p>
 * Setiap skill harus mengimplementasikan method:
 * <ul>
 * <li>{@link #getName()} - Nama skill</li>
 * <li>{@link #getFpCost()} - Biaya FP untuk menggunakan skill</li>
 * <li>{@link #use(BaseCharacter, BaseCharacter)} - Logika eksekusi skill</li>
 * </ul>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BaseCharacter
 */
public interface Skill {

    /**
     * Mendapatkan nama skill.
     * 
     * @return Nama skill yang ditampilkan di UI
     */
    String getName();

    /**
     * Mendapatkan biaya Focus Points untuk menggunakan skill.
     * 
     * @return Jumlah FP yang dibutuhkan (0 untuk skill gratis)
     */
    int getFpCost();

    /**
     * Mengeksekusi skill pada target.
     * 
     * <p>
     * Method ini adalah inti dari polimorfisme - setiap implementasi
     * skill (Attack, Heal, Buff, dll) akan memiliki behavior yang berbeda.
     * 
     * <p>
     * Implementasi harus:
     * <ol>
     * <li>Memeriksa dan mengurangi FP user dengan
     * {@link BaseCharacter#useFocusPoints(int)}</li>
     * <li>Melakukan efek skill (damage, heal, buff, dll)</li>
     * <li>Mencatat aktivitas ke logger</li>
     * </ol>
     * 
     * @param user   Karakter yang menggunakan skill
     * @param target Karakter yang menjadi target skill
     */
    void use(BaseCharacter user, BaseCharacter target);

    /**
     * Menentukan apakah skill ini menargetkan diri sendiri.
     * 
     * <p>
     * Default implementation mengembalikan false (menargetkan musuh).
     * Override method ini untuk skill yang menargetkan diri sendiri
     * seperti Heal atau Guard.
     * 
     * @return true jika skill menargetkan user, false jika menargetkan musuh
     */
    default boolean isSelfTargeting() {
        return false;
    }
}
