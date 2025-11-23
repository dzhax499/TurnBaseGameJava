package com.game.characters;

import com.game.skills.Skill;
import com.game.effects.StatusEffect;
import com.game.effects.FreezeEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

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
    private int speed;
    
    // FP (Focus Points) untuk penggunaan Skill
    private int focusPoints;
    private int maxFocusPoints;
    
    // Daftar Skill yang akan menampung objek turunan Skill (Polimorfisme)
    private final List<Skill> skills;
    
    // Daftar efek status aktif pada karakter
    private final List<StatusEffect> activeEffects;
    
    // Random number generator untuk dodge & crit
    private static final Random random = new Random();

    // ====================================================================
    // 2. KONSTRUKTOR
    // ====================================================================

    public BaseCharacter(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHealthPoints = maxHp;
        this.healthPoints = maxHp; // HP awal diatur ke maksimal
        this.attackPower = attack;
        this.defense = defense;
        this.speed = speed;
        
        // Inisialisasi FP default (bisa disesuaikan nanti)
        this.maxFocusPoints = 50; 
        this.focusPoints = 50; 
        
        this.skills = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
        
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
     * Logika dasar saat karakter menerima kerusakan (tanpa mekanik dodge/crit).
     * Digunakan untuk efek seperti Burn yang bypass dodge.
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
     * Versi takeDamage dengan mekanik Dodge dan Critical Hit.
     */
    public void takeDamageWithMechanics(int rawDamage, BaseCharacter attacker) {
        // 1. Cek Dodge
        double dodgeChance = calculateDodgeChance(attacker);
        if (random.nextDouble() * 100 < dodgeChance) {
            System.out.println(this.name + " menghindari serangan! (Dodge)");
            return;
        }
        
        // 2. Cek Critical Hit
        double critChance = attacker.calculateCritChance();
        boolean isCrit = random.nextDouble() * 100 < critChance;
        
        int finalDamage = rawDamage;
        if (isCrit) {
            finalDamage = (int) (rawDamage * 1.5);
            System.out.println(attacker.getName() + " melakukan Critical Hit!");
        }
        
        // 3. Apply Defense
        finalDamage = Math.max(0, finalDamage - this.defense);
        this.healthPoints -= finalDamage;
        if (this.healthPoints < 0) {
            this.healthPoints = 0;
        }
        
        System.out.println(this.name + " menerima " + finalDamage + " kerusakan." + (isCrit ? " (CRIT!)" : ""));
    }
    
    /**
     * Menghitung dodge chance berdasarkan Speed.
     */
    private double calculateDodgeChance(BaseCharacter attacker) {
        double chance = (this.speed - attacker.getSpeed()) / 100.0 * 100;
        return Math.min(30, Math.max(0, chance));
    }
    
    /**
     * Menghitung critical hit chance berdasarkan Speed.
     */
    private double calculateCritChance() {
        return Math.min(25, this.speed / 4.0);
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

    /**
     * Mengurangi FP saat menggunakan skill.
     * Mengembalikan true jika FP cukup, false jika tidak.
     */
    public boolean useFocusPoints(int amount) {
        if (this.focusPoints >= amount) {
            this.focusPoints -= amount;
            return true;
        }
        System.out.println(this.name + " tidak cukup FP!");
        return false;
    }

    /**
     * Regenerasi FP.
     */
    public void regenFocusPoints(int amount) {
        this.focusPoints += amount;
        if (this.focusPoints > this.maxFocusPoints) {
            this.focusPoints = this.maxFocusPoints;
        }
    }
    
    /**
     * Menambahkan efek status ke karakter.
     */
    public void addEffect(StatusEffect effect) {
        this.activeEffects.add(effect);
        effect.apply(this);
    }
    
    /**
     * Memproses semua efek aktif. Dipanggil setiap awal giliran.
     */
    public void processEffects() {
        Iterator<StatusEffect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            effect.tick(this);
            
            if (!effect.decreaseDuration()) {
                effect.remove(this);
                iterator.remove();
            }
        }
    }
    
    /**
     * Cek apakah karakter bisa bergerak (tidak terkena Freeze).
     */
    public boolean canMove() {
        for (StatusEffect effect : activeEffects) {
            if (effect instanceof FreezeEffect) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Mendapatkan daftar efek aktif (untuk display).
     */
    public List<StatusEffect> getActiveEffects() {
        return new ArrayList<>(activeEffects);
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
    public int getSpeed() { return speed; }
    public int getFocusPoints() { return focusPoints; }
    public int getMaxFocusPoints() { return maxFocusPoints; }
    public List<Skill> getSkills() { return skills; }

    // Setters
    public void setAttackPower(int attackPower) { this.attackPower = attackPower; }
    public void setDefense(int defense) { this.defense = defense; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setMaxFocusPoints(int maxFp) { 
        this.maxFocusPoints = maxFp; 
        this.focusPoints = maxFp; 
    }
}