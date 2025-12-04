package com.utils;

/**
 * Centralized configuration untuk semua konstanta game.
 * Menghindari magic numbers dan memudahkan game balancing.
 * 
 * @version 1.2 - REBALANCED
 */
public final class Constants {

    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class tidak boleh diinstansiasi");
    }

    // ====================================================================
    // CHARACTER BASE STATS
    // ====================================================================

    /**
     * Default Focus Points untuk semua karakter.
     */
    public static final int DEFAULT_MAX_FP = 50;
    public static final int DEFAULT_STARTING_FP = 50;

    // ====================================================================
    // CHARACTER TYPE STATS - REBALANCED (All Total: 180)
    // ====================================================================

    // Fire Character - Aggressive Attacker (Total: 180)
    public static final int FIRE_MAX_HP = 105; // +5 (More sustain)
    public static final int FIRE_ATTACK = 32; // -3 (Balanced aggro)
    public static final int FIRE_DEFENSE = 18; // +3 (Better survivability)
    public static final int FIRE_SPEED = 25; // -5 (Standard speed)

    // Water Character - Defensive Controller (Total: 182)
    public static final int WATER_MAX_HP = 115; // Unchanged
    public static final int WATER_ATTACK = 30; // +2 (Better offense)
    public static final int WATER_DEFENSE = 22; // -3 (Trade def for atk)
    public static final int WATER_SPEED = 15; // +3 (Slightly faster)

    // Earth Character - Ultimate Tank (Total: 178)
    public static final int EARTH_MAX_HP = 125; // -5 (Not too spongey)
    public static final int EARTH_ATTACK = 24; // +2 (Threatening enough)
    public static final int EARTH_DEFENSE = 24; // -4 (Breakable defense)
    public static final int EARTH_SPEED = 5; // +5 (Not absolute zero)

    // Wind Character - Glass Cannon (Total: 182)
    public static final int WIND_MAX_HP = 100; // +5 (Survive 2 hits)
    public static final int WIND_ATTACK = 30; // -2 (Balanced with speed)
    public static final int WIND_DEFENSE = 12; // +4 (Not paper thin)
    public static final int WIND_SPEED = 40; // -5 (Still fastest)

    // ====================================================================
    // BATTLE MECHANICS - DODGE SYSTEM
    // ====================================================================

    /**
     * Divisor untuk menghitung dodge chance dari speed difference.
     * Higher value = lower dodge chance.
     * Formula: (defender.speed - attacker.speed) / DODGE_SPEED_DIVISOR * 100
     */
    public static final double DODGE_SPEED_DIVISOR = 200.0;

    /**
     * Maximum dodge chance percentage (cap).
     */
    public static final double MAX_DODGE_CHANCE = 20.0;

    // ====================================================================
    // BATTLE MECHANICS - CRITICAL HIT SYSTEM
    // ====================================================================

    /**
     * Base critical hit chance untuk semua karakter (percentage).
     */
    public static final double BASE_CRIT_CHANCE = 5.0;

    /**
     * Divisor untuk menghitung crit bonus dari speed.
     * Formula: speed / CRIT_SPEED_DIVISOR
     */
    public static final double CRIT_SPEED_DIVISOR = 8.0;

    /**
     * Maximum critical hit chance percentage (cap).
     */
    public static final double MAX_CRIT_CHANCE = 20.0;

    /**
     * Critical hit damage multiplier.
     */
    public static final double CRIT_DAMAGE_MULTIPLIER = 1.5;

    // ====================================================================
    // BATTLE MECHANICS - ELEMENTAL ADVANTAGE
    // ====================================================================

    /**
     * Damage multiplier saat memiliki elemental advantage.
     */
    public static final double ELEMENTAL_ADVANTAGE_MULTIPLIER = 1.5;

    /**
     * Damage multiplier saat memiliki elemental disadvantage.
     */
    public static final double ELEMENTAL_DISADVANTAGE_MULTIPLIER = 0.75;

    /**
     * Damage multiplier untuk neutral matchup.
     */
    public static final double ELEMENTAL_NEUTRAL_MULTIPLIER = 1.0;

    // ====================================================================
    // BATTLE MECHANICS - FOCUS POINTS
    // ====================================================================

    /**
     * FP regeneration per turn cycle (setelah kedua pemain bermain).
     * REBALANCED: Increased untuk faster-paced battles.
     */
    public static final int FP_REGEN_PER_TURN = 8;

    // ====================================================================
    // SKILL COSTS - REBALANCED
    // ====================================================================

    public static final int ATTACK_SKILL_FP_COST = 0;
    public static final int GUARD_SKILL_FP_COST = 0;
    public static final int HEAL_SKILL_FP_COST = 12; // REBALANCED: 10 → 12

    // Elemental Skills - REBALANCED
    public static final int FIREBALL_FP_COST = 18; // REBALANCED: 15 → 18
    public static final int ICE_BLAST_FP_COST = 20; // REBALANCED: 15 → 20
    public static final int ROCK_THROW_FP_COST = 15; // Unchanged
    public static final int AIR_SLASH_FP_COST = 12; // REBALANCED: 15 → 12

    // ====================================================================
    // STATUS EFFECTS
    // ====================================================================

    /**
     * Burn effect damage per turn.
     * REBALANCED: Reduced untuk balance.
     */
    public static final int BURN_DAMAGE_PER_TURN = 2;

    /**
     * Burn effect default duration (turns).
     */
    public static final int BURN_DEFAULT_DURATION = 3;

    /**
     * Freeze effect default duration (turns).
     */
    public static final int FREEZE_DEFAULT_DURATION = 1;

    /**
     * Guard effect defense multiplier.
     * REBALANCED: Increased untuk make defensive play more viable.
     */
    public static final double GUARD_DEFENSE_MULTIPLIER = 2.5;

    /**
     * Guard effect default duration (turns).
     */
    public static final int GUARD_DEFAULT_DURATION = 1;

    /**
     * Attack buff multiplier.
     */
    public static final double ATTACK_BUFF_MULTIPLIER = 1.5;

    /**
     * Attack buff default duration (turns).
     */
    public static final int ATTACK_BUFF_DEFAULT_DURATION = 2;

    // ====================================================================
    // SKILL DAMAGE/HEALING VALUES - REBALANCED
    // ====================================================================

    /**
     * Heal skill base healing amount.
     * REBALANCED: Increased untuk encourage healing strategies.
     */
    public static final int HEAL_SKILL_AMOUNT = 35;

    /**
     * Fireball skill damage multiplier.
     * REBALANCED: Reduced untuk balance.
     */
    public static final double FIREBALL_DAMAGE_MULTIPLIER = 1.4;

    /**
     * Ice Blast skill damage multiplier.
     * REBALANCED: Reduced karena freeze effect sangat kuat.
     */
    public static final double ICE_BLAST_DAMAGE_MULTIPLIER = 1.3;

    /**
     * Rock Throw skill damage multiplier.
     * REBALANCED: Increased untuk compensate no status effect.
     */
    public static final double ROCK_THROW_DAMAGE_MULTIPLIER = 1.6;

    /**
     * Air Slash skill damage multiplier.
     * REBALANCED: Increased untuk spam potential.
     */
    public static final double AIR_SLASH_DAMAGE_MULTIPLIER = 1.5;

    // ====================================================================
    // VALIDATION LIMITS
    // ====================================================================

    /**
     * Minimum valid HP value.
     */
    public static final int MIN_HP = 1;

    /**
     * Maximum valid HP value.
     */
    public static final int MAX_HP = 999;

    /**
     * Minimum valid stat value (Attack, Defense, Speed).
     */
    public static final int MIN_STAT = 0;

    /**
     * Maximum valid stat value (Attack, Defense, Speed).
     */
    public static final int MAX_STAT = 999;

    /**
     * Maximum character name length.
     */
    public static final int MAX_NAME_LENGTH = 50;

    // ====================================================================
    // UI CONSTANTS
    // ====================================================================

    /**
     * HP bar visual length (characters).
     */
    public static final int HP_BAR_LENGTH = 20;

    /**
     * FP bar visual length (characters).
     */
    public static final int FP_BAR_LENGTH = 10;

    /**
     * Battle log maximum entries.
     */
    public static final int MAX_BATTLE_LOG_ENTRIES = 100;
}
