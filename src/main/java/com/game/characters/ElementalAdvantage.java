package com.game.characters;

/**
 * Utility class untuk menghitung elemental advantage.
 * Menentukan damage multiplier berdasarkan matchup elemen.
 */
public class ElementalAdvantage {

    // Damage multipliers
    private static final double ADVANTAGE_MULTIPLIER = 1.2;
    private static final double NEUTRAL_MULTIPLIER = 1.0;
    private static final double DISADVANTAGE_MULTIPLIER = 0.9;

    /**
     * Menghitung damage multiplier berdasarkan elemental matchup.
     * 
     * @param attackerElement Elemen penyerang
     * @param defenderElement Elemen defender
     * @return Multiplier untuk damage (0.75, 1.0, atau 1.5)
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
     * Cek apakah attacker punya advantage terhadap defender.
     * 
     * Elemental Cycle: Fire > Wind > Earth > Water > Fire
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
     * Mendapatkan deskripsi effectiveness untuk UI.
     * 
     * @param attacker Elemen penyerang
     * @param defender Elemen defender
     * @return Message effectiveness (empty string jika neutral)
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
