package com.game.characters;

import java.util.ArrayList;
import java.util.List;
import com.game.skills.Skill;

/**
 * Kelas abstrak yang menjadi dasar untuk semua karakter dalam game.
 * Mengimplementasikan prinsip Abstraksi, Pewarisan, dan Enkapsulasi.
 */
public abstract class BaseCharacter {

    // 1. ATRIBUT INTI (PRIVATE untuk Enkapsulasi)
    private String name;
    private int healthPoints;
    private int maxHealthPoints;
    
    private int attackPower;
    private int defense;
    private int magicPower;
    private int speed;
    
    // Daftar Skill yang akan menampung objek turunan Skill (Polimorfisme)
    private final List<Skill> skills;

    // ====================================================================
    // 2. KONSTRUKTOR
    // ====================================================================

    public BaseCharacter(String name, int maxHp, int attack, int defense, int magic, int speed) {
        this.name = name;
        this.maxHealthPoints = maxHp;
        this.healthPoints = maxHp; // HP awal diatur ke maksimal
        this.attackPower = attack;
        this.defense = defense;
        this.magicPower = magic;
        this.speed = speed;
        this.skills = new ArrayList<>();
        
        // Panggil metode abstrak. Subclass wajib mengisi daftar skill-nya di sini.
        initializeSkills(); 
    }
    
    // ====================================================================
    // 3. METODE ABSTRAK (Diharuskan ada di subclass - Abstraksi)
    // ====================================================================
    
    /**
     * Metode ini harus diimplementasikan oleh setiap subclass (Warrior, Mage, dsb.)
     * untuk menambahkan 4 skill spesifik mereka ke dalam daftar 'skills'.
     */
    protected abstract void initializeSkills(); 

    // ====================================================================
    // 4. METODE KONKRET (Dapat digunakan oleh semua subclass)
    // ====================================================================

    /**
     * Logika dasar saat karakter menerima kerusakan.
     * Menggunakan nilai 'defense' untuk mengurangi 'rawDamage'.
     */
    public void takeDamage(int rawDamage) {
        int finalDamage = Math.max(0, rawDamage - this.defense);
        this.healthPoints -= finalDamage;
        if (this.healthPoints < 0) {
            this.healthPoints = 0;
        }
        System.out.println(this.name + " menerima " + finalDamage + " kerusakan.");
    }
    
    /**
     * Logika dasar untuk menyembuhkan HP.
     * Tidak akan melebihi maxHealthPoints.
     */
    public void heal(int healAmount) {
        this.healthPoints += healAmount;
        if (this.healthPoints > this.maxHealthPoints) {
            this.healthPoints = this.maxHealthPoints;
        }
        System.out.println(this.name + " menyembuhkan diri sebesar " + healAmount + " HP.");
    }

    // Cek Status
    public boolean isAlive() {
        return this.healthPoints > 0;
    }
    
    // Helper untuk menambahkan skill (dipanggil dari initializeSkills() subclass)
    protected void addSkill(Skill skill) {
        this.skills.add(skill);
    }
    
    // ====================================================================
    // 5. GETTERS & SETTERS (Akses data yang Terenkapsulasi)
    // ====================================================================

    // Getters
    public String getName() { return name; }
    public int getHealthPoints() { return healthPoints; }
    public int getMaxHealthPoints() { return maxHealthPoints; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public int getMagicPower() { return magicPower; }
    public int getSpeed() { return speed; }
    public List<Skill> getSkills() { return skills; } // Polimorfisme di sini

    // Setters (Contoh untuk stats yang dapat diubah oleh buff/debuff)
    public void setAttackPower(int attackPower) { this.attackPower = attackPower; }
    // Setters (Contoh untuk stats yang dapat diubah oleh buff/debuff)

}