package com.game.characters;

/**
 * Utility class untuk menghitung elemental advantage dalam combat.
 * 
 * ElementalAdvantage menentukan damage multiplier berdasarkan matchup
 * elemen antara attacker dan defender. Mengimplementasikan sistem
 * rock-paper-scissors untuk elemental combat.
 * 
 * Elemental Cycle:
 * Fire > Wind > Earth > Water > Fire
 * 
 * Damage Multipliers:
 * - Advantage (super effective): 1.2x damage
 * - Neutral (same element atau no matchup): 1.0x damage
 * - Disadvantage (not very effective): 0.9x damage
 * 
 * Contoh:
 * - Fire vs Wind = 1.2x (advantage)
 * - Fire vs Fire = 1.0x (neutral)
 * - Fire vs Water = 0.9x (disadvantage)
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see ElementType
 */
public class ElementalAdvantage {

    /** Multiplier untuk advantage (super effective) */
    private static final double ADVANTAGE_MULTIPLIER = 1.2;

    /** Multiplier untuk neutral matchup */
    private static final double NEUTRAL_MULTIPLIER = 1.0;

    /** Multiplier untuk disadvantage (not very effective) */
    private static final double DISADVANTAGE_MULTIPLIER = 0.9;

    /**
     * Private constructor untuk mencegah instantiation.
     * Class ini hanya menyediakan static methods.
     */
    private ElementalAdvantage() {

    }

    /**
     * Menghitung damage multiplier berdasarkan elemental matchup.
     * 
     * Logika:
     * 1. Jika salah satu NEUTRAL element -> 1.0x
     * 2. Jika same element -> 1.0x
     * 3. Jika attacker punya advantage -> 1.2x
     * 4. Jika defender punya advantage -> 0.9x
     * 5. Lainnya -> 1.0x
     * 
     * @param attackerElement Elemen penyerang
     * @param defenderElement Elemen defender
     * @return Multiplier untuk damage (0.9, 1.0, atau 1.2)
     */
    public static double getMultiplier(ElementType attackerElement, ElementType defenderElement) {
        // Neutral element tidak punya advantage/disadvantage
        if (attackerElement == ElementType.NEUTRAL || defenderElement == ElementType.NEUTRAL) {
            return NEUTRAL_MULTIPLIER;
        }

        // Same element = neutral
        if (attackerElement == defenderElement) {
            return NEUTRAL_MULTIPLIER;
        }

        // Check advantage
        if (hasAdvantage(attackerElement, defenderElement)) {
            return ADVANTAGE_MULTIPLIER;
        }

        // Check disadvantage
        if (hasAdvantage(defenderElement, attackerElement)) {
            return DISADVANTAGE_MULTIPLIER;
        }

        return NEUTRAL_MULTIPLIER;
    }

    /**
     * Mengecek apakah attacker punya advantage terhadap defender.
     * 
     * Elemental Cycle: Fire > Wind > Earth > Water > Fire
     * 
     * @param attacker Elemen penyerang
     * @param defender Elemen defender
     * @return true jika attacker punya advantage
     */
    private static boolean hasAdvantage(ElementType attacker, ElementType defender) {
        switch (attacker) {
            case FIRE:
                return defender == ElementType.WIND;
            case WIND:
                return defender == ElementType.EARTH;
            case EARTH:
                return defender == ElementType.WATER;
            case WATER:
                return defender == ElementType.FIRE;
            default:
                return false;
        }
    }

    /**
     * Mendapatkan pesan effectiveness untuk ditampilkan di UI.
     * 
     * @param attacker Elemen penyerang
     * @param defender Elemen defender
     * @return Pesan effectiveness, atau empty string jika neutral
     */
    public static String getEffectivenessMessage(ElementType attacker, ElementType defender) {
        double multiplier = getMultiplier(attacker, defender);

        if (multiplier > NEUTRAL_MULTIPLIER) {
            return "Sangat efektif!";
        } else if (multiplier < NEUTRAL_MULTIPLIER) {
            return "Kurang efektif...";
        }
        return "";
    }
}
