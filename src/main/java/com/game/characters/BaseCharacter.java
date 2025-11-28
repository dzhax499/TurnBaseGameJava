package com.game.characters;

import com.game.skills.Skill;
import com.game.skills.effects.StatusEffect;
import com.game.skills.effects.FreezeEffect;
import com.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

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

    // ====================================================================
    // 2. KONSTRUKTOR
    // ====================================================================

    public BaseCharacter(String name, int maxHp, int attack, int defense, int speed) {
        // Input validation
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama karakter tidak boleh kosong");
        }
        if (name.length() > Constants.MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Nama karakter terlalu panjang (max " + Constants.MAX_NAME_LENGTH + " karakter)");
        }
        if (maxHp < Constants.MIN_HP || maxHp > Constants.MAX_HP) {
            throw new IllegalArgumentException("Max HP harus antara " + Constants.MIN_HP + " dan " + Constants.MAX_HP);
        }
        if (attack < Constants.MIN_STAT || attack > Constants.MAX_STAT) {
            throw new IllegalArgumentException(
                    "Attack harus antara " + Constants.MIN_STAT + " dan " + Constants.MAX_STAT);
        }
        if (defense < Constants.MIN_STAT || defense > Constants.MAX_STAT) {
            throw new IllegalArgumentException(
                    "Defense harus antara " + Constants.MIN_STAT + " dan " + Constants.MAX_STAT);
        }
        if (speed < Constants.MIN_STAT || speed > Constants.MAX_STAT) {
            throw new IllegalArgumentException(
                    "Speed harus antara " + Constants.MIN_STAT + " dan " + Constants.MAX_STAT);
        }

        this.name = name.trim();
        this.maxHealthPoints = maxHp;
        this.healthPoints = maxHp; // HP awal diatur ke maksimal
        this.attackPower = attack;
        this.defense = defense;
        this.speed = speed;

        // Inisialisasi FP dari Constants
        this.maxFocusPoints = Constants.DEFAULT_MAX_FP;
        this.focusPoints = Constants.DEFAULT_STARTING_FP;

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

    /**
     * Mendapatkan tipe elemen karakter.
     * Harus diimplementasikan oleh setiap subclass.
     * 
     * @return ElementType karakter (FIRE, WATER, EARTH, WIND, atau NEUTRAL)
     */
    public abstract ElementType getElementType();

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
     * Versi takeDamage dengan mekanik Dodge, Critical Hit, dan Elemental Advantage.
     */
    public void takeDamageWithMechanics(int rawDamage, BaseCharacter attacker) {
        // 1. Cek Dodge (using ThreadLocalRandom for thread safety)
        double dodgeChance = calculateDodgeChance(attacker);
        if (ThreadLocalRandom.current().nextDouble() * 100 < dodgeChance) {
            System.out.println(this.name + " menghindari serangan! (Dodge)");
            return;
        }

        // 2. Cek Critical Hit (using ThreadLocalRandom for thread safety)
        double critChance = attacker.calculateCritChance();
        boolean isCrit = ThreadLocalRandom.current().nextDouble() * 100 < critChance;

        int finalDamage = rawDamage;
        if (isCrit) {
            finalDamage = (int) (rawDamage * Constants.CRIT_DAMAGE_MULTIPLIER);
            System.out.println(attacker.getName() + " melakukan Critical Hit!");
        }

        // 3. Apply Elemental Advantage
        double elementalMultiplier = ElementalAdvantage.getMultiplier(
                attacker.getElementType(),
                this.getElementType());
        finalDamage = (int) (finalDamage * elementalMultiplier);

        // Display effectiveness message
        String effectivenessMsg = ElementalAdvantage.getEffectivenessMessage(
                attacker.getElementType(),
                this.getElementType());
        if (!effectivenessMsg.isEmpty()) {
            System.out.println(effectivenessMsg);
        }

        // 4. Apply Defense
        finalDamage = Math.max(0, finalDamage - this.defense);
        this.healthPoints -= finalDamage;
        if (this.healthPoints < 0) {
            this.healthPoints = 0;
        }

        System.out.println(this.name + " menerima " + finalDamage + " kerusakan." + (isCrit ? " (CRIT!)" : ""));
    }

    /**
     * Menghitung dodge chance berdasarkan Speed.
     * BALANCED: Reduced speed impact untuk gameplay yang lebih fair.
     */
    private double calculateDodgeChance(BaseCharacter attacker) {
        double speedDiff = this.speed - attacker.getSpeed();
        double chance = speedDiff / Constants.DODGE_SPEED_DIVISOR * 100;
        return Math.min(Constants.MAX_DODGE_CHANCE, Math.max(0, chance));
    }

    /**
     * Menghitung critical hit chance berdasarkan Speed.
     * BALANCED: Added base crit untuk semua karakter, reduced speed impact.
     */
    private double calculateCritChance() {
        double baseCrit = Constants.BASE_CRIT_CHANCE;
        double speedBonus = this.speed / Constants.CRIT_SPEED_DIVISOR;
        return Math.min(Constants.MAX_CRIT_CHANCE, baseCrit + speedBonus);
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

    /**
     * Mendapatkan nama-nama efek aktif sebagai List<String>.
     * Digunakan untuk display di battle UI.
     */
    public List<String> getActiveEffectNames() {
        List<String> effectNames = new ArrayList<>();
        for (StatusEffect effect : activeEffects) {
            effectNames.add(effect.getName());
        }
        return effectNames;
    }

    /**
     * Update status effects (alias untuk processEffects).
     * Digunakan untuk memproses semua efek aktif setiap turn.
     */
    public void updateStatusEffects() {
        processEffects();
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
    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getFocusPoints() {
        return focusPoints;
    }

    public int getMaxFocusPoints() {
        return maxFocusPoints;
    }

    public List<Skill> getSkills() {
        // Defensive copy untuk mencegah external modification
        return new ArrayList<>(skills);
    }

    // Setters
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMaxFocusPoints(int maxFp) {
        this.maxFocusPoints = maxFp;
        this.focusPoints = maxFp;
    }
}